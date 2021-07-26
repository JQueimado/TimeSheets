package com.example.gokart.main_activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.lifecycle.LiveData
import com.example.gokart.R
import com.example.gokart.data_converters.toTextTimeStamp
import com.example.gokart.database.entity.*
import java.util.*

class TimeSheet() {
    //View
    companion object{

        //Resources
        private const val timeSheetView = R.layout.view_time_sheet
        private const val timeSheetItemView = R.layout.view_time_sheet_row

        //Time Sheet Components
        private const val timeSheetDate = R.id.time_sheets_date
        private const val timeSheetKart = R.id.time_sheet_kart
        private const val timeSheetKartingCenter = R.id.time_sheet_karting_center
        private const val timeSheetBestLap = R.id.time_sheet_best_lap
        private const val timeSheetWorstLap = R.id.time_sheet_worst_lap
        private const val timeSheetAverageLap = R.id.time_sheet_average_lap
        private const val timeSheetConsistency = R.id.time_sheet_consistency

        //Laps Components
        private const val lapsFrameID = R.id.times_sheet
        private const val lapNumber = R.id.time_sheet_lap_number
        private const val lapValue = R.id.time_sheet_lap
        private const val lapBestDelta = R.id.time_sheet_bestlap_delta
        private const val lapLastDelta = R.id.time_sheet_lastlap_delta

        //Inflate
        fun inflate( parent : ViewGroup, inflater : LayoutInflater, timeSheetWithLaps: TimeSheetWithLaps): View {
            //Values
            val laps = timeSheetWithLaps.laps

            //Inflate main view
            val view = inflater.inflate(timeSheetView, parent, false )
            val buttonsFrame = view.findViewById<ConstraintLayout>(R.id.time_sheet_button_popup)
            buttonsFrame.visibility = View.GONE

            //Click Action
            view.isClickable = true
            view.setOnClickListener {
                buttonsFrame.visibility = View.GONE
            }

            //Hold Action
            view.setOnLongClickListener {
                buttonsFrame.visibility = View.VISIBLE
                return@setOnLongClickListener true
            }

            //On delete click
            view.findViewById<Button>(R.id.time_sheet_delete_button).setOnClickListener {
                //TODO

            }

            //On Edit click
            view.findViewById<Button>(R.id.time_sheet_edit_button).setOnClickListener {
                //TODO
            }

            //set sheet view
            val lapsFrame = view.findViewById<TableLayout>(lapsFrameID)

            Log.d("numberOfLaps", laps.size.toString())

            for ( e in laps ){
                val row = inflater.inflate(timeSheetItemView, lapsFrame, false)
                lapsFrame.addView(row)
            }
            return view
        }

        //SetValues
        fun setValues(
            timeSheetWithLaps: TimeSheetWithLaps,
            itemView: View,
            kart: LiveData<KartEntity>,
            kartingCenter: LiveData<KartingCenterEntity>,
            activity: AppCompatActivity
        ){
            val timeSheet = timeSheetWithLaps.timeSheet
            val laps = timeSheetWithLaps.laps

            //top values
            setSheetStats(itemView, timeSheet, kart, kartingCenter, activity)

            //lap values
            val lapsFrame = itemView.findViewById<TableLayout>(lapsFrameID)
            val lapsViews = lapsFrame.children.toList()

            for( i in 1 until lapsViews.size){
                setLapValues( lapsViews[i], laps[i-1] )
            }
        }

        //Injects
        @SuppressLint("SetTextI18n")
        private fun setSheetStats(
            view: View,
            timeSheetEntity: TimeSheetEntity,
            kart: LiveData<KartEntity>,
            kartingCenter: LiveData<KartingCenterEntity>,
            activity : AppCompatActivity
        ){
            //Date
            val date = Date(timeSheetEntity.date)
            val dateText : String = DateFormat.format("dd/MM/yyyy hh:mm", date).toString()
            view.findViewById<TextView>(timeSheetDate).text = dateText

            //Kart
            kart.observe( activity, {
                //DefineName
                val name = if( it.name.isBlank() )
                    "${it.number}-${it.displacement}cc"
                else
                    it.name
                //Set name
                view.findViewById<TextView>(timeSheetKart).text = name
            } )

            //KartingCenter
            kartingCenter.observe( activity, {
                view.findViewById<TextView>(timeSheetKartingCenter).text = it.name
            } )

            //Best lap
            view.findViewById<TextView>(timeSheetBestLap)
                .text = timeSheetEntity.bestLap.toTextTimeStamp()

            //Worst lap
            view.findViewById<TextView>(timeSheetWorstLap)
                .text = timeSheetEntity.worstLap.toTextTimeStamp()

            //Average lap
            view.findViewById<TextView>(timeSheetAverageLap)
                .text = timeSheetEntity.averageLap.toTextTimeStamp()

            //Consistency
            view.findViewById<TextView>(timeSheetConsistency)
                .text = "${timeSheetEntity.consistency}/100%"
        }

        //Injects values into a time sheet item view
        private fun setLapValues(view : View, sheetValues: LapEntity){
            //Lap Number
            view.findViewById<TextView>(lapNumber).text = sheetValues.number.toString()
            //Lap time
            view.findViewById<TextView>(lapValue).text = sheetValues.time
            //Lap best delta
            view.findViewById<TextView>(lapBestDelta).text = sheetValues.bestDelta
            //Lap last delta
            view.findViewById<TextView>(lapLastDelta).text = sheetValues.lastDelta
        }
    }
}
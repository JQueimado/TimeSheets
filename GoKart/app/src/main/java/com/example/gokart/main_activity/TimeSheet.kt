package com.example.gokart.main_activity

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.example.gokart.R
import com.example.gokart.database.entity.LapEntity
import com.example.gokart.database.entity.TimeSheetEntity
import com.example.gokart.database.entity.TimeSheetWithLaps

class TimeSheet() {
    //View
    companion object{

        //Resources
        private const val timeSheetView = R.layout.view_time_sheet
        private const val timeSheetItemView = R.layout.view_time_sheet_row

        //IDS
        private const val lapsFrameID = R.id.times_sheet

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
        fun setValues( timeSheetWithLaps: TimeSheetWithLaps, itemView: View ){
            val timeSheet = timeSheetWithLaps.timeSheet
            val laps = timeSheetWithLaps.laps

            //top values
            setSheetStats(itemView, timeSheet)

            //lap values
            val lapsFrame = itemView.findViewById<TableLayout>(lapsFrameID)
            val lapsViews = lapsFrame.children.toList()

            for( i in 1 until lapsViews.size){
                setLapValues( lapsViews[i], laps[i-1] )
            }
        }

        //Injects
        private fun setSheetStats( view: View, timeSheetEntity: TimeSheetEntity ){

        }

        //Injects values into a time sheet item view
        private fun setLapValues(view : View, sheetValues: LapEntity){
            //Lap Number
            view.findViewById<TextView>(R.id.time_sheet_lap_number)
                .text = sheetValues.number.toString()

            //Lap time
            view.findViewById<TextView>(R.id.time_sheet_lap)
                .text = sheetValues.time

            //Lap best delta
            view.findViewById<TextView>(R.id.time_sheet_bestlap_delta)
                .text = sheetValues.bestDelta

            //Lap last delta
            view.findViewById<TextView>(R.id.time_sheet_lastlap_delta)
                .text = sheetValues.lastDelta
        }
    }
}
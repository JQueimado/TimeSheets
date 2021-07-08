package com.example.gokart.main_activity

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gokart.R
import com.example.gokart.database.entity.LapEntity
import com.example.gokart.database.entity.TimeSheetEntity
import com.example.gokart.database.entity.TimeSheetWithLaps
import kotlin.random.Random

class TimeSheet( values: TimeSheetWithLaps ) {

    private val values : TimeSheetWithLaps = values
    val viewList : MutableList<View> = ArrayList()

    //View
    companion object{
        //Inflate
        fun inflate( parent : ViewGroup, inflater : LayoutInflater, timeSheet: TimeSheet): View {
            //set view
            val view = inflater.inflate(R.layout.view_time_sheet, parent, false )

            view.findViewById<ConstraintLayout>(R.id.time_sheet_button_popup).visibility = View.GONE

            //Buttons
            view.isClickable = true
            view.setOnClickListener {
                view.findViewById<ConstraintLayout>(R.id.time_sheet_button_popup).visibility =
                    View.GONE
            }
            view.setOnLongClickListener {
                view.findViewById<ConstraintLayout>(R.id.time_sheet_button_popup).visibility = View.VISIBLE
                return@setOnLongClickListener true
            }

            //On delete click
            view.findViewById<Button>(R.id.time_sheet_delete_button).setOnClickListener {
                //TODO

            }

            view.findViewById<Button>(R.id.time_sheet_edit_button).setOnClickListener {
                //TODO
            }

            //set sheet view
            val sheetLayout = view.findViewById<TableLayout>(R.id.times_sheet)
            for ( e in timeSheet.values.laps ){
                val row = inflater.inflate(R.layout.view_time_sheet_row, sheetLayout, false)
                sheetLayout.addView(row)
                timeSheet.viewList.add(row)
            }

            return view
        }

        //SetValues
        fun setValues( timeSheet: TimeSheet){
            //top values
            //TODO
            //sheet values
            for( i in 0 until timeSheet.viewList.size){
                setLapValue( timeSheet.viewList[i], timeSheet.values.laps[i] )
            }
        }

        private fun setLapValue( view : View, sheetValues: LapEntity){
            view.findViewById<TextView>(R.id.time_sheet_lap_number).text = sheetValues.number.toString()
            view.findViewById<TextView>(R.id.time_sheet_lap).text = sheetValues.time
            view.findViewById<TextView>(R.id.time_sheet_bestlap_delta).text = sheetValues.bestDelta
            view.findViewById<TextView>(R.id.time_sheet_lastlap_delta).text = sheetValues.lastDelta
        }
    }
}
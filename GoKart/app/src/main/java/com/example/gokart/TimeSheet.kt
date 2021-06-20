package com.example.gokart

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

class TimeSheet() {

    val viewList : MutableList<View> = ArrayList()
    val contentList : MutableList<SheetValues> = ArrayList()
    private var laps : Int = 0

    fun addDefRow( ){
        val ii = Random.nextInt(1,15)
        for ( i in 0..ii )
            addRow( "1.1.1111", "+1.111", "+1")
    }

    fun addRow( time : String, bestlap_delta : String, last_delta : String) {
        laps += 1
        contentList.add( SheetValues(laps, time, bestlap_delta, last_delta) )
    }

    //View
    companion object{
        //Inflate
        fun inflate( parent : ViewGroup, inflater : LayoutInflater, timeSheet: TimeSheet ): View {
            //set view
            val view = inflater.inflate( R.layout.view_time_sheet, parent, false )

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
            for ( e in timeSheet.contentList ){
                val row = inflater.inflate( R.layout.view_time_sheet_row, sheetLayout, false)
                sheetLayout.addView(row)
                timeSheet.viewList.add(row)
            }

            Log.d("TimeSheet", "viewlistSize:"+timeSheet.viewList.size)
            Log.d("TimeSheet", "contentlistSize:"+timeSheet.contentList.size)

            return view
        }

        //SetValues
        fun setValues( timeSheet: TimeSheet ){
            //top values
            //TODO
            //sheet values
            for( i in 0 until timeSheet.viewList.size){
                setLapValue( timeSheet.viewList[i], timeSheet.contentList[i] )
            }
        }

        private fun setLapValue( view : View, sheetValues: SheetValues ){
            view.findViewById<TextView>(R.id.time_sheet_lap_number).text = sheetValues.lap.toString()
            view.findViewById<TextView>(R.id.time_sheet_lap).text = sheetValues.time
            view.findViewById<TextView>(R.id.time_sheet_bestlap_delta).text = sheetValues.bestlap_delta
            view.findViewById<TextView>(R.id.time_sheet_lastlap_delta).text = sheetValues.last_delta
        }
    }

    //Data Holder
    class SheetValues(val lap : Int,val time : String, val bestlap_delta: String, val last_delta: String)
}
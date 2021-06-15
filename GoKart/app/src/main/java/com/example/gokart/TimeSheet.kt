package com.example.gokart

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView

class TimeSheet() {

    val viewList : MutableList<View> = ArrayList()
    val contentList : MutableList<SheetValues> = ArrayList()

    fun addDefRow( ){
        addRow(1, "1.1.1111", "+1.111", "+1")
        addRow(2, "1.1.1111", "+1.111", "+1")
    }

    fun addRow( lap : Int, time : String, bestlap_delta : String, last_delta : String) {
        contentList.add( SheetValues(lap, time, bestlap_delta, last_delta) )
    }

    //View
    companion object{
        //Inflate
        fun inflate( parent : ViewGroup, inflater : LayoutInflater, timeSheet: TimeSheet ): View {
            //set view
            val view = inflater.inflate( R.layout.fragment_table, parent, false )

            //set sheet view
            val sheetLayout = view.findViewById<TableLayout>(R.id.times_sheet)
            for ( e in timeSheet.contentList ){
                val row = inflater.inflate( R.layout.timesheetrow, sheetLayout, false)
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
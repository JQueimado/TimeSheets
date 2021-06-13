package com.example.gokart

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView

class TimeSheetView( context : Context, inflater : LayoutInflater ) : LinearLayout( context ), MainActivityContentView {

    private val inflater : LayoutInflater = inflater

    override fun inflate(){
        inflater.inflate( R.layout.fragment_table, this )

        var timeSheet : TableLayout = this!!.findViewById(R.id.times_sheet)

        var temp : View = addRow(1, "1.1.1111", "+1.111", "+1")
        timeSheet.addView(temp)

        temp = addRow(2, "1.1.1111", "+1.111", "+1")
        timeSheet.addView(temp)

    }

    @SuppressLint("ResourceAsColor")
    private fun addRow (lap : Int, time : String, bestlap_delta : String, last_delta : String ) : View{
        val row : View = inflater.inflate(R.layout.timesheetrow, null)

        row.findViewById<TextView>(R.id.time_sheet_lap_number).setText(lap.toString())
        row.findViewById<TextView>(R.id.time_sheet_lap).setText(time)
        row.findViewById<TextView>(R.id.time_sheet_bestlap_delta).setText(bestlap_delta)
        row.findViewById<TextView>(R.id.time_sheet_lastlap_delta).setText(last_delta)

        return row
    }
}
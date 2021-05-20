package com.example.gokart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class TimeSheetFragment : Fragment(R.layout.fragment_table) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        var timeSheet : TableLayout = view!!.findViewById(R.id.times_sheet)

        var temp : View = addRow(1, "1.1.1111", "+1.111", "+1")
        timeSheet.addView(temp)

        temp = addRow(2, "1.1.1111", "+1.111", "+1")
        timeSheet.addView(temp)

        return view
    }

    @SuppressLint("ResourceAsColor")
    private fun addRow (lap : Int, time : String, bestlap_delta : String, last_delta : String ) : View{
        val row : View = layoutInflater.inflate(R.layout.timesheetrow, null)

        row.findViewById<TextView>(R.id.time_sheet_lap_number).setText(lap.toString())
        row.findViewById<TextView>(R.id.time_sheet_lap).setText(time)
        row.findViewById<TextView>(R.id.time_sheet_bestlap_delta).setText(bestlap_delta)
        row.findViewById<TextView>(R.id.time_sheet_lastlap_delta).setText(last_delta)

        return row
    }

}
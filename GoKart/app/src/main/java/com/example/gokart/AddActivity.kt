package com.example.gokart

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.add
import androidx.fragment.app.commit
import java.util.*

class AddActivity : AppCompatActivity(R.layout.activity_add){

    private val datePicker : DatePickerFragment = DatePickerFragment(this);
    private val timePicker : TimePickerFragment = TimePickerFragment(this)
    private val kartPicker : ChoseKartFragment = ChoseKartFragment(this)

    private var date : Date = Date()
    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    fun onDatePick( year: Int, month : Int, day: Int){
        this.year = year
        this.month = month
        this.day = day

        timePicker.show(supportFragmentManager, "TimePicker")
    }

    fun onTimePick( hour : Int, minute : Int ){
        date = Date( year, month, day, hour, minute )
        val dateText : String = DateFormat.format("dd/MM/yyyy hh:mm", date).toString()
        findViewById<Button>(R.id.date_picker_button).setText(dateText)
    }

    fun onPickKartBack(){
        findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.GONE

        //Fragment manager
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add( R.id.picker_fragment, kartPicker)
        }

        //Back button
        findViewById<Button>(R.id.nav_back_button).setOnClickListener( View.OnClickListener {
            finish()
        })

        //Date picker
        findViewById<Button>(R.id.date_picker_button).setOnClickListener( View.OnClickListener {
            datePicker.show(supportFragmentManager, "date_picker")
        })

        //Pick kart
        findViewById<Button>(R.id.pick_kart_button).setOnClickListener(View.OnClickListener {
            findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.VISIBLE
        })

    }
}
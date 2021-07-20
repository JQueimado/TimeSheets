package com.example.gokart.add_activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.example.gokart.*
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class AddActivity : AppCompatActivity(R.layout.activity_add){

    //Testing vars
    private lateinit var kartList : MutableList<String>
    private lateinit var kartingCenterList : MutableList<String>

    //Fragments
    private val datePicker : DatePickerFragment = DatePickerFragment(this)
    private val timePicker : TimePickerFragment = TimePickerFragment(this)
    private lateinit var kartPicker : PickerFragment
    private lateinit var kartingCenterPicker : PickerFragment
    private lateinit var addKartFragment : AddKartFragment

    //Date Values
    private var date : Date = Date()
    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    //Lap values
    private var lapCount : Int = 0

    //On Set Date and Time Click
    fun onDatePick( year: Int, month : Int, day: Int){
        this.year = year
        this.month = month
        this.day = day

        timePicker.show(supportFragmentManager, "TimePicker")
    }

    //PopsUp after date selection
    fun onTimePick( hour : Int, minute : Int ){
        Date( year - 1900, month, day, hour, minute ).also { date = it }
        val dateText : String = DateFormat.format("dd/MM/yyyy hh:mm", date).toString()
        findViewById<Button>(R.id.date_picker_button).text = dateText
    }

    //Result for Picking a kart
    fun onPickKartConfirm(name : String){
        findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.GONE
        findViewById<Button>(R.id.pick_kart_button).text = name
        supportFragmentManager.commit {
            hide(kartPicker)
        }
    }

    //Result for Picking a Karting Center
    fun onPickKartingCenterConfirm(name : String){
        findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.GONE
        findViewById<Button>(R.id.pick_kart_center_button).text = name
        supportFragmentManager.commit {
            hide(kartingCenterPicker)
        }
    }

    //Kart Control
    fun onAddKartPress(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            hide(kartPicker)
            show(addKartFragment)
        }
    }

    fun onAddKartBackPress(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            hide(addKartFragment)
            show(kartPicker)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //////////Test///////////
        kartList = ArrayList()
        for (i in 1..20) {
            kartList.add( Random.nextInt(0,100).toString() + "-50cc" )
        }

        kartingCenterList = ArrayList()
        kartingCenterList.add("Some Karting Center 1")
        kartingCenterList.add("Some Karting Center 2")
        kartingCenterList.add("Some Karting Center 3")
        kartingCenterList.add("Some Karting Center 4")
        ////////////////////////

        //Create Fragments
        kartPicker = PickerFragment(this, kartList, PickerFragment.KART_MODE)
        kartingCenterPicker = PickerFragment( this,kartingCenterList, PickerFragment.KARTING_CENTER_MODE )
        addKartFragment = AddKartFragment(this)

        findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.GONE

        //Fragment manager
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.picker_fragment, kartPicker)
            add(R.id.picker_fragment, kartingCenterPicker)
            add(R.id.picker_fragment, addKartFragment)

            hide(kartPicker)
            hide(kartingCenterPicker)
            hide(addKartFragment)
        }

        //Back button
        findViewById<Button>(R.id.nav_back_button).setOnClickListener {
            finish()
        }

        //Date picker
        findViewById<Button>(R.id.date_picker_button).setOnClickListener {
            datePicker.show(supportFragmentManager, "date_picker")
        }

        //Pick karting center
        findViewById<Button>(R.id.pick_kart_center_button).setOnClickListener {
            supportFragmentManager.commit {
                hide( kartPicker )
                show( kartingCenterPicker )
            }
            findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.VISIBLE
        }

        //Pick kart
        findViewById<Button>(R.id.pick_kart_button).setOnClickListener {
            kartPicker.setData(kartList)
            supportFragmentManager.commit {
                hide(kartingCenterPicker)
                show(kartPicker)
            }
            findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.VISIBLE
        }

        //Add List
        val listLayout : LinearLayout = findViewById(R.id.add_laps_layout)

        findViewById<ConstraintLayout>(R.id.add_lap_button).setOnClickListener {
            lapCount += 1
            findViewById<TextView>(R.id.add_laps_counter).text =
                resources.getText(R.string.add_list_title).toString() + lapCount.toString()
            listLayout.addView( AddLapView( applicationContext, layoutInflater, lapCount ) )
        }

    }

    //Lap View
    @SuppressLint("ViewConstructor", "SetTextI18n")
    class AddLapView// Lap Tag

    //Time input
    constructor(
        context: Context,
        inflater: LayoutInflater,
        count: Int
    ) : ConstraintLayout(context) {

        init {
            inflater.inflate(R.layout.view_add_lap_row, this, true )
            this.findViewById<TextView>(R.id.lap_number).text = "$count-0:00.000"
            val textView : TextView = findViewById(R.id.lap_number)
            this.findViewById<TextInputEditText>(R.id.lap_input).addTextChangedListener(
                object : TextWatcher{

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        var text = p0.toString()

                        for (i in text.length..6 )
                            text = "0$text"

                        Log.d(text, "yez")

                        text = text.substring(0, text.length-5) +
                                ":" + text.substring(text.length-5, text.length-3) +
                                "." + text.substring(text.length-3, text.length)

                        text = "$count-$text"
                        textView.text = text
                    }

                    override fun afterTextChanged(p0: Editable?) {

                    }
                })
        }

    }
}
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

class AddActivity : AppCompatActivity(R.layout.activity_add){

    private val datePicker : DatePickerFragment = DatePickerFragment(this);
    private val timePicker : TimePickerFragment = TimePickerFragment(this)
    private val kartingCenterAndKartPicker = ItemPickerView(
        this
    )

    private var date : Date = Date()
    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    private var lapCount : Int = 0

    fun onDatePick( year: Int, month : Int, day: Int){
        this.year = year
        this.month = month
        this.day = day

        timePicker.show(supportFragmentManager, "TimePicker")
    }

    fun onTimePick( hour : Int, minute : Int ){
        date = Date( year - 1900, month, day, hour, minute )
        val dateText : String = DateFormat.format("dd/MM/yyyy hh:mm", date).toString()
        findViewById<Button>(R.id.date_picker_button).setText(dateText)
    }

    fun onPickKartBack(){
        findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.GONE
    }

    fun onPickKartConfirm(name : String){
        findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.GONE
        findViewById<Button>(R.id.pick_kart_button).setText(name)
    }

    fun onAddKartPress(){
        val addKartFragment = PickerAddFragment(this)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.picker_fragment, addKartFragment)
        }
    }

    fun onAddKartBackPress(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.picker_fragment, kartingCenterAndKartPicker)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<FragmentContainerView>(R.id.picker_fragment).visibility = View.GONE

        //Fragment manager
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.picker_fragment, kartingCenterAndKartPicker)
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
    class AddLapView : ConstraintLayout{

        constructor(context : Context, inflater : LayoutInflater, count : Int ) : super(context){
            inflater.inflate(R.layout.view_add_lap_row, this, true )

            // Lap Tag
            this.findViewById<TextView>(R.id.lap_number).text = "$count-0:00.000"

            //Time input
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
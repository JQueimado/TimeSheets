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
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.commit
import com.example.gokart.R
import com.example.gokart.data_converters.toIntTimeSheet
import com.example.gokart.database.entity.*
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList

class AddActivity : AppCompatActivity(R.layout.activity_add){

    //ViewModels
    private val addActivityKartingCenterViewModel: AddActivityKartingCenterViewModel by viewModels()
    private val addActivityKartViewModel: AddActivityKartViewModel by viewModels()
    private val addActivityTimeSheetViewModel : AddActivityTimeSheetViewModel by viewModels()

    //Fragments
    private var placerID = R.id.picker_fragment
    private val datePicker : DatePickerFragment = DatePickerFragment(this)
    private val timePicker : TimePickerFragment = TimePickerFragment(this)
    private lateinit var kartPicker : PickerFragment
    private lateinit var kartingCenterPicker : PickerFragment
    private lateinit var addKartFragment : AddKartFragment
    private lateinit var addKartingCenterFragment: AddKartingCenterFragment

    //Date Values
    private var date : Date = Date()
    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    //UI
    private lateinit var choseDate : Button
    private lateinit var choseKartButton: Button
    private lateinit var choseKartingCenterButton: Button
    private lateinit var doneButton: Button
    private lateinit var backButton: Button

    //Lap values
    private var lapCount : Int = 0
    private var lapViews : MutableList<AddLapView> = ArrayList()

    //Selections
    private var kartingCenter: KartingCenterWithKarts? = null
    private var kart : KartEntity? = null
    private var kartList : MutableList<String>? = null
    private var kartingCenterList : MutableList<String>? = null

    //Interaction
    private var interaction = true

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
    fun onPickKartConfirm(item : Int){
        supportFragmentManager.popBackStack()
        if( item != -1 ) {
            choseKartButton.text = kartList!![item]
            //kartList is generated by karts so is safe to assume they are in the same order
            kart = kartingCenter!!.karts[item]
        } else {
            choseKartButton.text = resources.getString(R.string.add_pick_kart)
            kart = null
        }
    }

    //Result for Picking a Karting Center
    fun onPickKartingCenterConfirm(name : String){
        supportFragmentManager.popBackStack()
        if(name.isEmpty()) {
            choseKartButton.isEnabled = false
            choseKartingCenterButton.text = resources.getString(R.string.add_pick_kart_center)
            choseKartButton.text = resources.getString(R.string.add_pick_kart)
            kartingCenter = null
        }else{
            //If nothing changes nothing happens
            if( kartingCenter != null && kartingCenter!!.kartingCenterEntity.name == name )
                return

            choseKartButton.isEnabled = true //Enables kart search
            choseKartingCenterButton.text = name
            choseKartButton.text = resources.getString(R.string.add_pick_kart)

            //Observer is here because it needs to be updated with the name on karting center pick
            addActivityKartingCenterViewModel.getOneByName(name).observe(this, {
                kartingCenter = it //Update karting center

                //update karts
                kartList = ArrayList()
                if( kartingCenter != null ) { //just in case
                    for (kartEntity in kartingCenter!!.karts) {
                        kartList!!.add(
                            if (kartEntity.name.isEmpty())
                                "${kartEntity.number}-${kartEntity.displacement}cc"
                            else
                                kartEntity.name
                        )
                    }
                    kartPicker.setData(kartList!!)
                }
            })

        }
    }

    //Kart Add Control
    fun onOpenAddKart(){
        supportFragmentManager.commit {
            replace(placerID, addKartFragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun onAddKartConclude( kartEntity: KartEntity ){
        addActivityKartViewModel.insert(kartEntity)
        onCloseAddKart()
    }

    fun onCloseAddKart(){
        supportFragmentManager.popBackStack()
    }

    //Karting Center Add Control
    fun onOpenAddKartingCenter(){
        supportFragmentManager.commit {
            replace( placerID, addKartingCenterFragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun onAddKartingCenterConclude( kartingCenterEntity: KartingCenterEntity ){
        addActivityKartingCenterViewModel.insert(kartingCenterEntity)
        //Close Fragment
        onCloseAddKartingCenter()
    }

    fun onCloseAddKartingCenter(){
        supportFragmentManager.popBackStack()
    }

    ////////// On Create //////////
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kartingCenterList = ArrayList()
        kartingCenterList = ArrayList()
        kartList = ArrayList()

        //Create Fragments
        kartPicker = PickerFragment(this, kartList!!, PickerFragment.KART_MODE)
        kartingCenterPicker = PickerFragment(
            this,
            kartingCenterList!!,
            PickerFragment.KARTING_CENTER_MODE
        )
        addKartFragment = AddKartFragment(this)
        addKartingCenterFragment = AddKartingCenterFragment(this)

        findViewById<View>(R.id.picker_fragment).visibility = View.VISIBLE

        //Data observers
        addActivityKartingCenterViewModel.getAllNames().observe(this, {
            kartingCenterPicker.setData(it)
        })

        //Back button
        backButton = findViewById<Button>(R.id.nav_back_button)
        backButton.setOnClickListener {
            finish()
        }

        //Date picker
        choseDate = findViewById<Button>(R.id.date_picker_button)
        choseDate.setOnClickListener {
            datePicker.show(supportFragmentManager, "date_picker")
        }

        //Pick karting center
        choseKartingCenterButton = findViewById(R.id.pick_kart_center_button)
        choseKartingCenterButton.setOnClickListener {
            supportFragmentManager.commit {
                replace(placerID, kartingCenterPicker )
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        //Pick kart
        choseKartButton = findViewById(R.id.pick_kart_button)
        choseKartButton.isEnabled = false
        choseKartButton.setOnClickListener {
            supportFragmentManager.commit {
                replace(placerID, kartPicker)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        //Add List
        val listLayout : LinearLayout = findViewById(R.id.add_laps_layout)

        findViewById<ConstraintLayout>(R.id.add_lap_button).setOnClickListener {
            lapCount += 1
            findViewById<TextView>(R.id.add_laps_counter).text =
                resources.getText(R.string.add_list_title).toString() + lapCount.toString()

            val addLapView = AddLapView( applicationContext, layoutInflater, lapCount )
            //Add to view
            listLayout.addView( addLapView )
            //Add to list
            lapViews.add( addLapView )
        }

        //Done Button
        doneButton = findViewById(R.id.add_conclude_button)
        doneButton.setOnClickListener{

            //values and variables
            val lapsValue = arrayListOf<Int>()
            val lapsText = arrayListOf<String>()

            //Verification and lap conversion
            try{
                //karting Center
                if (kartingCenter == null)
                    throw Exception( "Invalid Karting Center" )

                //kart
                if( kart == null )
                    throw Exception( "Invalid Kart" )

                //laps
                if( lapViews.size == 0 )
                    throw Exception("Laps required")

                for ( lap in lapViews ){

                    if(lap.text.isBlank() )
                        throw Exception("Lap with no value")

                    lapsValue.add( lap.text.toIntTimeSheet() )
                    lapsText.add( lap.text )
                }

            }catch (e: Exception ){
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //Assemble data structures and insert into database
            addActivityTimeSheetViewModel.insert(
                kartingCenterEntity = kartingCenter!!.kartingCenterEntity,
                kartEntity = kart!!,
                date = date,
                lapsValue = lapsValue,
                lapsText = lapsText,
                activity = this
            )

            //Disable interaction
            doneButton.isEnabled = false
            backButton.isEnabled = false
            choseDate.isEnabled = false
            choseKartingCenterButton.isEnabled = false
            choseKartButton.isEnabled = false
            interaction = false
        }
    }

    fun getKartingCenterID() : Long{
        return if( kartingCenter != null )
            kartingCenter!!.kartingCenterEntity.kartingCenterId
        else
            -1
    }

    //Lap View
    @SuppressLint("ViewConstructor", "SetTextI18n")
    class AddLapView (context: Context, inflater: LayoutInflater, count: Int) : ConstraintLayout(context) {// Lap Tag

        var text = ""

        init {
            inflater.inflate(R.layout.view_add_lap_row, this, true)
            this.findViewById<TextView>(R.id.lap_number).text = "$count-0:00.000"
            val textView: TextView = findViewById(R.id.lap_number)
            this.findViewById<TextInputEditText>(R.id.lap_input)
                .addTextChangedListener( object : TextWatcher {

                    private var value : String = ""

                    override fun beforeTextChanged(
                        p0: CharSequence?,
                        p1: Int,
                        p2: Int,
                        p3: Int
                    ) {
                        //Nothing needed
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        var text = p0.toString()

                        for (i in text.length..6)
                            text = "0$text"

                        Log.d(text, "yez")

                        text = text.substring(0, text.length - 5) +
                                ":" + text.substring(text.length - 5, text.length - 3) +
                                "." + text.substring(text.length - 3, text.length)

                        value = text
                        text = "$count-$text"
                        textView.text = text
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        this@AddLapView.text = value
                    }
                })
        }
    }
}
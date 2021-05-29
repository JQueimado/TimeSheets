package com.example.gokart

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

class ChoseKartFragment(parent : AddActivity) : Fragment() {

    val parent : AddActivity = parent

    private val listCheck : ArrayList<ChoseKartItem> = ArrayList()
    private var picked : ChoseKartItem? = null
    private var lastCommited : ChoseKartItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //On pick
    fun onClick( view : View ){
        val item : ChoseKartItem = view as ChoseKartItem

        if ( picked == null ){ // All Clear -> Select
            item.setChecked()
            picked = item
        }else {
            if(picked?.getName()?.let { item.getName().compareTo(it) } == 0){ //Action over choice
                picked?.setUnchecked()
                picked = null
            }else{ //Chose different
                picked?.setUnchecked()
                picked = item
                picked?.setChecked()
            }
        }

    }

    private fun clearAllChoices(){
        for ( l in listCheck ){
            l.setUnchecked()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(
            R.layout.fragment_chose_kart,
            container,
            false)

        //Back Button
        view.findViewById<Button>(R.id.choose_kart_back_button).setOnClickListener(
            View.OnClickListener {
                parent.onPickKartBack()
                picked?.setUnchecked()
                lastCommited?.setChecked()
                picked = lastCommited
        })

        //On Confirm click
        view.findViewById<Button>(R.id.choose_kart_confirm_button).setOnClickListener(
            View.OnClickListener {
                if( parent == null )
                    parent.onPickKartConfirm( "" )
                else
                    picked?.let { it1 -> parent.onPickKartConfirm( it1.getName() ) }

                lastCommited = picked
        })

        val list : LinearLayout = view.findViewById(R.id.choose_kart_karts_list)

        //Assemble list
        for (i in 1..20) {

            //Create view
            val temp : ChoseKartItem = ChoseKartItem(
                Random.nextInt(0,100).toString() + "-50cc",
                parent,
                inflater,
                this
            )
            listCheck.add(temp)
            list.addView(temp)
        }

        return view
    }

    //Item Picker
    class ChoseKartItem : ConstraintLayout{

        constructor( name : String, context : Context, inflater: LayoutInflater, parent: ChoseKartFragment ): super(context){

            inflater.inflate(R.layout.view_kart_picker_object, this, true)

            //Set name
            this.findViewById<TextView>(R.id.picker_kart_name).setText(name)

            //Set On click
            this.setOnClickListener {
                parent.onClick(this)
            }
        }

        fun getName() : String{
            return this.findViewById<TextView>(R.id.picker_kart_name).text.toString()
        }

        fun setChecked(){
            this.findViewById<CheckBox>(R.id.chose_kart_checkbox).isChecked = true
        }

        fun setUnchecked(){
            this.findViewById<CheckBox>(R.id.chose_kart_checkbox).isChecked = false
        }

    }

}
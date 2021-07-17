package com.example.gokart.add_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.gokart.R

class PickerAddFragment(val parent: AddActivity) : Fragment() {
    private var mode : Short = KART_MODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_kart, container, false)

        //OK
        view.findViewById<Button>(R.id.add_kart_confirm_button).setOnClickListener {
            parent.onAddKartBackPress()
        }

        //Back
        view.findViewById<Button>(R.id.add_kart_back_button).setOnClickListener {
            parent.onAddKartBackPress()
        }

        return view
    }

    fun set_Mode( mode: Short ){

        if( this.mode == mode )
            return;



        this.mode = mode
    }

    companion object{
        val KART_MODE : Short = 0
        val KARTING_CENTER_MODE : Short = 1
    }

}
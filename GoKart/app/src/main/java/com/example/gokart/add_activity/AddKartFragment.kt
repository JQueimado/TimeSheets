package com.example.gokart.add_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.gokart.R

class AddKartFragment( parent : AddActivity) : Fragment() {

    val parent : AddActivity = parent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_add_kart, container, false)

        //Back
        view.findViewById<Button>(R.id.add_kart_back_button).setOnClickListener {
            parent.onAddKartBackPress()
        }

        //OK
        view.findViewById<Button>(R.id.add_kart_confirm_button).setOnClickListener {
            parent.onAddKartBackPress()
        }

        return view
    }

}
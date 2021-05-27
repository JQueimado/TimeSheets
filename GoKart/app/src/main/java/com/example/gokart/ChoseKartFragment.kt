package com.example.gokart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.solver.state.State
import androidx.constraintlayout.widget.ConstraintLayout

class ChoseKartFragment(parent : AddActivity) : Fragment() {

    val parent : AddActivity = parent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_chose_kart, container, false)

        //Back Button
        view.findViewById<Button>(R.id.choose_kart_back_button).setOnClickListener(View.OnClickListener {
            parent.onPickKartBack()
        })

        val list : LinearLayout = view.findViewById(R.id.choose_kart_karts_list)

        for (i in 1..3) {

            val temp = ConstraintLayout(parent)
            inflater.inflate(R.layout.view_kart_picker_object, temp, false)
            list.addView(temp)

        }

        return view
    }
}
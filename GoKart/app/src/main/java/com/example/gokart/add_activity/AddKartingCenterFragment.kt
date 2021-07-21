package com.example.gokart.add_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.gokart.R

class AddKartingCenterFragment( val addActivity: AddActivity ) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_add_karting_center,
            container,
            false
        )

        //Back Button
        view.findViewById<Button>(R.id.add_karting_center_back_button).setOnClickListener{
            addActivity.onCloseAddKartingCenter()
        }

        return view
    }
}
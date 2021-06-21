package com.example.gokart.main_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gokart.R

class Stats() {

    companion object {

        fun inflate( parent: ViewGroup, inflater: LayoutInflater) : View {
            return inflater.inflate(R.layout.view_statistics, parent, false)
        }

        fun setValues() {
            //TODO
        }
    }

}
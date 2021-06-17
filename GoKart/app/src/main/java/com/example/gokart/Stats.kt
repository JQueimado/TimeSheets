package com.example.gokart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
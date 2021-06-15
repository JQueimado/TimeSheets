package com.example.gokart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TableLayout

class Stats() {

    companion object {

        fun inflate( parent: ViewGroup, inflater: LayoutInflater) : View {
            return inflater.inflate(R.layout.fragment_stats, parent, false)
        }

        fun setValues() {
            //TODO
        }
    }

}
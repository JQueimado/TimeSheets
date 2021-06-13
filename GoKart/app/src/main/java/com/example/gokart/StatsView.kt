package com.example.gokart

import android.content.Context
import android.view.LayoutInflater
import android.widget.TableLayout

class StatsView( context: Context, inflater: LayoutInflater ) : TableLayout(context), MainActivityContentView{

    private val inflater : LayoutInflater = inflater

    override fun inflate(){
        inflater.inflate( R.layout.fragment_stats, this )
    }

}
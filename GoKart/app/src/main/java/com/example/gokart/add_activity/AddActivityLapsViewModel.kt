package com.example.gokart.add_activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class AddActivityLapsViewModel(application: Application) : AndroidViewModel(application) {
    private val lapsToAdd:MutableList<String> = ArrayList()

    fun get(i:Int): String{
        return lapsToAdd[i]
    }

    fun get(): MutableList<String>{
        return lapsToAdd
    }

    fun set( i:Int, string: String ){
         lapsToAdd[i] = string
    }

    fun add( string: String ){
        lapsToAdd.add(string)
    }

    fun size(): Int{
        return lapsToAdd.size
    }

    fun remove( i: Int ){
        lapsToAdd.removeAt(i)
    }
}
package com.example.gokart.add_activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterWithKarts

class AddActivityDataViewModel(application: Application) : AndroidViewModel(application) {

    //Selections
    var kartingCenter: KartingCenterWithKarts? = null
    var kart : KartEntity? = null
    var kartList : MutableList<String> = ArrayList()
    var kartingCenterList : MutableList<String> = ArrayList()

    //Laps
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
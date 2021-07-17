package com.example.gokart.add_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartEntity

class KartsViewModel(application: Application) : AndroidViewModel(application) {
    private val kartsDao = AppDatabase.getMemoryInstance(application).kartDao()
    private val karts = kartsDao.getAllSimple()

    fun insert( kartEntity: KartEntity ){
        kartsDao.addKart(kartEntity)
    }

    fun getAll() : LiveData<List<KartEntity>>{
        return karts
    }
}
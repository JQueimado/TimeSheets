package com.example.gokart.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartEntity
import kotlinx.coroutines.launch

class KartsViewModel(application: Application) : AndroidViewModel(application) {
    private val kartsDao = AppDatabase.getMemoryInstance(application).kartDao()
    private val karts = kartsDao.getAllSimple()

    fun insert( kartEntity: KartEntity ){
        viewModelScope.launch {
            kartsDao.addKart(kartEntity)
        }
    }

    fun getAll() : LiveData<List<KartEntity>>{
        return karts
    }
}
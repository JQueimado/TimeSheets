package com.example.gokart.add_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartEntity
import kotlinx.coroutines.launch

class AddActivityKartViewModel(application: Application) : AndroidViewModel(application) {

    private val kartDao = AppDatabase.getInstance(application).kartDao()

    fun insert( kartEntity: KartEntity ){
        viewModelScope.launch {
            kartDao.addKart(kartEntity)
        }
    }

}
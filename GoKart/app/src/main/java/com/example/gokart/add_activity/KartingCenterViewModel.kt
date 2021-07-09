package com.example.gokart.add_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartingCenterEntity

class KartingCenterViewModel( application: Application ) : AndroidViewModel(application) {

    private val kartingCenterDAO = AppDatabase.getMemoryInstance(application).kartingCenterDao()
    private val allSimpleKC = kartingCenterDAO.getAllSimple()

    fun insert( kartingCenterEntity: KartingCenterEntity ){
        kartingCenterDAO.addKartingCenter(kartingCenterEntity)
    }

    fun getAll() : LiveData<List<KartingCenterEntity>>{
        return allSimpleKC
    }

}
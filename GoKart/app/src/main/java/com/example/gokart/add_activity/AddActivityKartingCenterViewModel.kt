package com.example.gokart.add_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.KartingCenterWithKarts
import kotlinx.coroutines.launch

class AddActivityKartingCenterViewModel(application: Application ) : AndroidViewModel(application) {

    private val kartingCenterDAO = AppDatabase.getInstance(application).kartingCenterDao()
    private val allSimpleKC = kartingCenterDAO.getAllNames()

    fun insert(kartingCenterEntity: KartingCenterEntity ){
        viewModelScope.launch {
            kartingCenterDAO.addKartingCenter(kartingCenterEntity)
        }
    }

    fun getAllNames() : LiveData<MutableList<String>>{
        return allSimpleKC
    }

    fun getOneByName( name : String ) : LiveData<KartingCenterWithKarts>{
        return kartingCenterDAO.getOneComplexByName( name )
    }

}
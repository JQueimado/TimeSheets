package com.example.gokart.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.TimeSheetWithLaps

class MainActivityDatabaseViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getMemoryInstance(application)
    private val timeSheetDao = database.timeSheetDao()
    private val kartDao = database.kartDao()
    private val kartingCenterDao = database.kartingCenterDao()

    private val completeTimeSheets = timeSheetDao.getAllComplex()

    fun getTimeSheets() : LiveData<List<TimeSheetWithLaps>> {
        return completeTimeSheets
    }

    fun getKart( id: Long ) : LiveData<KartEntity>{
        return kartDao.getOneSimple( id )
    }

    fun getKartingCenter( id: Long ): LiveData<KartingCenterEntity>{
        return kartingCenterDao.getOneSimple( id )
    }

}
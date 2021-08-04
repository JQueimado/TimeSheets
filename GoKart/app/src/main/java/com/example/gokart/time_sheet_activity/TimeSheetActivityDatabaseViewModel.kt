package com.example.gokart.time_sheet_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.TimeSheetWithLaps

class TimeSheetActivityDatabaseViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val timeSheetDao = database.timeSheetDao()
    private val kartDao = database.kartDao()
    private val kartingCenterDao = database.kartingCenterDao()

    fun getTimeSheet( id: Long ): LiveData<TimeSheetWithLaps>{
        return timeSheetDao.getOneComplex(id)
    }

    fun getKart( kartId:Long ): LiveData<KartEntity>{
        return kartDao.getOneSimple(kartId)
    }

    fun getKartingCenter( kartingCenterId: Long ): LiveData<KartingCenterEntity>{
        return kartingCenterDao.getOneSimple(kartingCenterId)
    }

}
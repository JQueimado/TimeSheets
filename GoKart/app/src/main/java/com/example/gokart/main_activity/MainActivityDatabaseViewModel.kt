package com.example.gokart.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.StatsEntity
import com.example.gokart.database.entity.TimeSheetWithLaps
import kotlinx.coroutines.launch

class MainActivityDatabaseViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getMemoryInstance(application)
    private val lapsDao = database.lapDao()
    private val timeSheetDao = database.timeSheetDao()
    private val kartDao = database.kartDao()
    private val kartingCenterDao = database.kartingCenterDao()
    private val statsDao = database.statsDao()

    private val completeTimeSheets = timeSheetDao.getAllComplex()
    private val statsEntity = statsDao.getStats()

    fun getTimeSheets() : LiveData<List<TimeSheetWithLaps>> {
        return completeTimeSheets
    }

    fun getKart( id: Long ) : LiveData<KartEntity>{
        return kartDao.getOneSimple( id )
    }

    fun getKartingCenter( id: Long ): LiveData<KartingCenterEntity>{
        return kartingCenterDao.getOneSimple( id )
    }

    fun getStats(): LiveData<List<StatsEntity>>{
        return statsEntity
    }

    fun deleteTimeSheet(id: Long, callBack:()->Unit){
        viewModelScope.launch {
            val timeSheetWithLaps = timeSheetDao.getOneComplexBlocking(id)

            timeSheetDao.deleteTimeSheet(timeSheetWithLaps.timeSheet)

            for(lap in timeSheetWithLaps.laps)
                lapsDao.deleteLap(lap)

            callBack()
        }
    }
}
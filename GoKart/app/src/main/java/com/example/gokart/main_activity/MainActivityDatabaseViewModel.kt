package com.example.gokart.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.*
import kotlinx.coroutines.launch

class MainActivityDatabaseViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getMemoryInstance(application)
    private val lapsDao = database.lapDao()
    private val timeSheetDao = database.timeSheetDao()
    private val kartDao = database.kartDao()
    private val kartingCenterDao = database.kartingCenterDao()
    private val statsDao = database.statsDao()

    private val completeTimeSheets = timeSheetDao.getAllSimple()
    private val statsEntity = statsDao.getStats()

    fun getTimeSheets() : LiveData<List<TimeSheetEntity>> {
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

    fun deleteTimeSheet(id: Long ){
        viewModelScope.launch {
            val timeSheetWithLaps = timeSheetDao.getOneComplexBlocking(id)
            val timeSheet = timeSheetWithLaps.timeSheet

            //TimeSheet delete
            timeSheetDao.deleteTimeSheet(timeSheet)

            //Lap Delete
            for(lap in timeSheetWithLaps.laps)
                lapsDao.deleteLap(lap)

            //Update stats
            val stats = statsDao.getStatsBlocking()[0]

            stats.averageLapSum -= timeSheet.averageLap
            stats.consistencySum -= timeSheet.consistency
            stats.entryNumber -= 1

            if( stats.bestLap == timeSheet.bestLap ){
                //TODO( Find 2nd best lap )
            }

            if (stats.favouriteKartingCenter == timeSheet.kartingCenterId) {
                //TODO( calculate favourite karting center )
            }

            if ( stats.favouriteKart == timeSheet.kartId ) {
                //TODO( calculate favourite kart )
            }

            statsDao.update( stats )
        }
    }
}
package com.example.gokart.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.StatsEntity
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getMemoryInstance(application)
    private val kartDao = database.kartDao()
    private val kartingCenterDAO = database.kartingCenterDao()

    fun setStatsToView( statsEntity: StatsEntity, statsView: StatsView ){
        viewModelScope.launch {
            //BestLap
            statsView.setBestLap( statsEntity.bestLap )

            //AverageLap
            val averageLap = (statsEntity.averageLapSum/statsEntity.averageLapNum).toInt()
            statsView.setAverageLap(averageLap)

            //Consistency
            val consistency = (statsEntity.averageLapSum/statsEntity.averageLapNum).toInt()
            statsView.setConsistency(consistency)

            //Favourite Kart
            val kart = kartDao.getNameBlocking(statsEntity.favouriteKart)
            if( kart != null ) {
                statsView.setKart(
                    if (kart.name.isBlank()) "${kart.number}-${kart.displacement}cc"
                    else kart.name
                )
            }

            //Favourite KartingCenter
            val kartingCenter = kartingCenterDAO
                .getNameBlocking(statsEntity.favouriteKartingCenter)
            if( kartingCenter != null )
                statsView.setKartingCenter(kartingCenter)
        }
    }
}
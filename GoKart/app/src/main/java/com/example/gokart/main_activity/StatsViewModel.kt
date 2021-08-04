package com.example.gokart.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokart.R
import com.example.gokart.data_converters.toTextTimeStamp
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.StatsEntity
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val kartDao = database.kartDao()
    private val kartingCenterDAO = database.kartingCenterDao()

    fun setStatsToView( statsEntity: StatsEntity, statsView: StatsView ){
        viewModelScope.launch {
            //BestLap
            if( statsEntity.bestLap == -1 )
                statsView.setBestLap( getApplication<Application>().resources.getString( R.string.default_lap) )
            else
                statsView.setBestLap( statsEntity.bestLap.toTextTimeStamp() )

            if (statsEntity.entryNumber == 0){
                statsView.setAverageLap( 0 )
                statsView.setConsistency( 100 )
            }else{
                //AverageLap
                val averageLap = (statsEntity.averageLapSum.toDouble()/statsEntity.entryNumber).toInt()
                statsView.setAverageLap(averageLap)

                //Consistency
                val consistency = (statsEntity.consistencySum/statsEntity.entryNumber).toInt()
                statsView.setConsistency(consistency)
            }

            //Favourite Kart
            val kart = kartDao.getNameBlocking(statsEntity.favouriteKart)
            if( kart != null ) {
                statsView.setKart(
                    if (kart.name.isBlank()) "${kart.number}-${kart.displacement}cc"
                    else kart.name
                )
            }else{
                statsView.setKart(
                    getApplication<Application>().resources.getString( R.string.default_kart )
                )
            }

            //Favourite KartingCenter
            val kartingCenter = kartingCenterDAO
                .getNameBlocking(statsEntity.favouriteKartingCenter)
            if( kartingCenter != null ) {
                statsView.setKartingCenter(kartingCenter)
            }else{
                statsView.setKartingCenter(
                    getApplication<Application>().resources.getString( R.string.default_track )
                )
            }
        }
    }
}
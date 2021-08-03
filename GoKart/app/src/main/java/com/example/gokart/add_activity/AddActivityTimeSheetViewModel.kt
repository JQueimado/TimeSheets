package com.example.gokart.add_activity

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokart.data_converters.getFavouriteKart
import com.example.gokart.data_converters.getFavouriteKartingCenter
import com.example.gokart.data_converters.toStringDelta
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AddActivityTimeSheetViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        private const val statsId = 0L
    }

    private val database = AppDatabase.getMemoryInstance(application)
    private val timeSheetDao = database.timeSheetDao()
    private val lapsDao = database.lapDao()
    private val statsDao = database.statsDao()
    private val kartDao = database.kartDao()
    private val kartingCenterDao = database.kartingCenterDao()

    fun insert(
        kartingCenterEntity: KartingCenterEntity,
        kartEntity: KartEntity,
        date: Date,
        lapsText: List<String>,
        lapsValue: List<Int>,
        activity: Activity//Generated from lapsText in validation
    ){
        viewModelScope.launch { //Start a coroutine

            Toast.makeText(getApplication(), "Adding TimeSheet", Toast.LENGTH_LONG).show()

            val laps : MutableList<LapEntity> = ArrayList()
            var bestLap = -1
            var worstLap = 0
            val averageLap: Int
            var consistency: Int

            var currentLapValue : Int
            var currentLapText : String
            var sumAvg = 0L
            var sumCon = 0L
            var worstDelta = 0
            var bestDelta: Int
            var lastDelta = 0

            //Find Fastest Lap
            for( lap in lapsValue ) {
                //BestLap
                if (bestLap > lap || bestLap == -1)
                    bestLap = lap

                //Average
                sumAvg += lap
            }

            averageLap = (sumAvg/lapsText.size).toInt()

            //Process Laps
            for( i in lapsValue.indices){
                //Value extraction
                currentLapValue = lapsValue[i]
                currentLapText = lapsText[i]

                //Worst Lap
                if( worstLap < currentLapValue )
                    worstLap = currentLapValue

                //best Delta
                bestDelta = currentLapValue - bestLap

                //lastDelta
                if(i != 0) //first lap delta = 0
                    lastDelta = currentLapValue - lapsValue[i-1]

                //Consistency
                val averageDelta = averageLap - currentLapValue
                val deltaMod = if( averageDelta < 0 ) averageDelta*-1 else averageDelta
                sumCon += deltaMod
                if( worstDelta < deltaMod )
                    worstDelta = deltaMod

                //Create Entity
                val lapEntity = LapEntity(
                    0,
                    i+1,
                    currentLapText,
                    bestDelta.toStringDelta(),
                    lastDelta.toStringDelta()
                )
                laps.add(lapEntity)
            }

            //Final calculations
            consistency =  ( ( (sumCon.toFloat()/(lapsText.size) )/worstDelta ) * 100 ).toInt()
            consistency = 100 - consistency
            //Create Time Sheet Entity
            val timeSheetEntity = TimeSheetEntity(
                kartEntity.kartId,
                kartingCenterEntity.kartingCenterId,
                bestLap,
                worstLap,
                averageLap,
                consistency,
                date.time
            )

            //Insert
            val id = timeSheetDao.addTimeSheet(timeSheetEntity)

            for (lap in laps){
                lap.timeSheetId = id
                lapsDao.addLap(lap)
            }

            //Update Stats
            setStats( timeSheetEntity )

            //Final Message
            Toast.makeText(getApplication(), "Added TimeSheet", Toast.LENGTH_LONG).show()
        }
    }

    private fun setStats (timeSheet: TimeSheetEntity){
        viewModelScope.launch {
            try {
                val statsList = statsDao.getStatsBlocking()

                if (statsList.isEmpty())
                    throw Exception("No stats object")

                val stats = statsList[0]

                if(stats.bestLap > timeSheet.bestLap || stats.bestLap == -1)
                    stats.bestLap = timeSheet.bestLap

                stats.averageLapSum += timeSheet.averageLap
                stats.consistencySum += timeSheet.consistency
                stats.entryNumber += 1

                //Favourite Kart
                if( stats.favouriteKart != timeSheet.kartId || stats.favouriteKart == -1L )
                    stats.favouriteKart =  getFavouriteKart(database)

                if( stats.favouriteKartingCenter != timeSheet.kartingCenterId || stats.favouriteKartingCenter == -1L )
                    stats.favouriteKartingCenter = getFavouriteKartingCenter(database)

                statsDao.update(stats)

            } catch (e: Exception) {
                val stats = StatsEntity(
                    statsId,
                    timeSheet.bestLap,
                    timeSheet.averageLap.toLong(),
                    timeSheet.consistency.toLong(),
                    1,
                    timeSheet.kartId,
                    timeSheet.kartingCenterId
                )

                statsDao.insert(stats)
            }

        }
    }

}
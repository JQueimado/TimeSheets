package com.example.gokart.add_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.LapEntity
import com.example.gokart.database.entity.TimeSheetEntity
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AddActivityTimeSheetViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getMemoryInstance(application)
    private val timeSheetDao = database.timeSheetDao()

    fun insert(
        kartingCenterEntity: KartingCenterEntity,
        kartEntity: KartEntity,
        date: Date,
        lapsText: List<String>,
        lapsValue: List<Int> //Generated from lapsText in validation
    ){
        viewModelScope.launch { //Start a coroutine
            val laps : MutableList<LapEntity> = ArrayList()
            var bestLap = -1
            var worstLap = -1
            var averageLap = -1
            var consistency = -1

            var currentLapValue = 0
            var currentLapText = ""
            var sum = 0L
            var bestDelta = 0
            var lastDelta = 0
            //Process Laps
            for( i in lapsValue.indices){
                //Value extraction
                currentLapValue = lapsValue[i]
                currentLapText = lapsText[i]

                //BestLap
                if( bestLap > currentLapValue )
                    bestLap = currentLapValue

                //Worst Lap
                if( worstLap < currentLapValue || worstLap == -1 )
                    worstLap = currentLapValue

                //Average
                sum += currentLapValue

                //Consistency
                //TODO

                //best Delta
                bestDelta = currentLapValue - bestLap

                //lastDelta
                if(i != 0) //first lap delta = 0
                    lastDelta = currentLapValue - lapsValue[i-1]

                //Create Entity
                /*val lapEntity = LapEntity(
                    0,
                    i,
                    currentLapValue,
                    bestDelta,
                    lastDelta
                )*/
            }

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
            timeSheetDao
        }
    }

}
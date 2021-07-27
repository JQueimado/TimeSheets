package com.example.gokart.add_activity

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gokart.data_converters.toStringDelta
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.LapEntity
import com.example.gokart.database.entity.TimeSheetEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AddActivityTimeSheetViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getMemoryInstance(application)
    private val timeSheetDao = database.timeSheetDao()
    private val lapsDao = database.lapDao()

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
            val consistency = 0 //TODO

            var currentLapValue : Int
            var currentLapText : String
            var sum = 0L
            var bestDelta: Int
            var lastDelta = 0

            //Find Fastest Lap
            for( lap in lapsValue )
                if( bestLap > lap || bestLap == -1 )
                    bestLap = lap

            //Process Laps
            for( i in lapsValue.indices){
                //Value extraction
                currentLapValue = lapsValue[i]
                currentLapText = lapsText[i]

                //Worst Lap
                if( worstLap < currentLapValue )
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
            averageLap = (sum/lapsText.size).toInt()

            //Create Time Sheet Entity
            val timeSheetEntity = TimeSheetEntity(
                kartEntity.kartId,
                kartingCenterEntity.kartingCenterId,
                bestLap,
                worstLap,
                averageLap,
                0,
                date.time
            )

            //Insert
            val id = timeSheetDao.addTimeSheet(timeSheetEntity)

            for (lap in laps){
                lap.timeSheetId = id
                lapsDao.addLap(lap)
            }

            //Final Message
            Toast.makeText(getApplication(), "Added TimeSheet", Toast.LENGTH_LONG).show()
            activity.finish()
        }
    }

}
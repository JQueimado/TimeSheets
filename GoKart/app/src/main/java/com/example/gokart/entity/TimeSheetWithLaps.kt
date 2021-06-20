package com.example.gokart.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TimeSheetWithLaps(
    @Embedded val timeSheet : TimeSheetEntity,
    @Relation( parentColumn = "timeSheetId", entityColumn = "timeSheetId" ) val laps : List<LapEntity>
)
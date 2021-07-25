package com.example.gokart.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TimeSheetWithLaps(
    @Embedded val timeSheet : TimeSheetEntity,
    @Relation(
        parentColumn = "time_sheet_id",
        entityColumn = "lap_time_sheet"
    )
    val laps : List<LapEntity>
)
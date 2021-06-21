package com.example.gokart.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class KartWithTimeSheets (
     @Embedded val kart : KartEntity,
     @Relation( parentColumn = "kart_id", entityColumn = "time_sheet_kart" )
     val timeSheets : List<TimeSheetEntity>
)
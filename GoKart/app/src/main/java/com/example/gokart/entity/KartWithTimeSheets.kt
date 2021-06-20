package com.example.gokart.entity

import androidx.room.Embedded
import androidx.room.Relation

data class KartWithTimeSheets (
     @Embedded val kart : KartEntity,
     @Relation( parentColumn = "kartId", entityColumn = "kartId" )
     val timeSheets : List<TimeSheetEntity>
)
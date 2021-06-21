package com.example.gokart.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class KartingCenterWithKarts (
    @Embedded val kartingCenterEntity: KartingCenterEntity,

    @Relation( parentColumn = "karting_center_id", entityColumn = "kart_karting_center" )
    val karts : List<KartEntity>
)
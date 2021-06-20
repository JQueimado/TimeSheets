package com.example.gokart.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "karting_center")
data class KartingCenterEntity(
    @PrimaryKey(autoGenerate = true) val kartingCenterId : Int,
    @ColumnInfo(name = "karting_center_name") val name : String,
    @ColumnInfo(name = "karting_center_location") val location : String,
    @ColumnInfo(name = "karting_center_visits") val visits : Int
)
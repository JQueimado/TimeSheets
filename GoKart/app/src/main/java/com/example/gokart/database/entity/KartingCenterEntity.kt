package com.example.gokart.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "karting_center")
data class
KartingCenterEntity(
    @ColumnInfo(name = "karting_center_name") val name : String,
    @ColumnInfo(name = "karting_center_location") val location : String,
    @ColumnInfo(name = "karting_center_visits") val visits : Int
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "karting_center_id")
    val kartingCenterId : Int = 0
}
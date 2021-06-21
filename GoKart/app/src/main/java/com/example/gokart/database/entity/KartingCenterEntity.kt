package com.example.gokart.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "karting_center")
data class KartingCenterEntity(
    @ColumnInfo(name = "karting_center_name") var name : String,
    @ColumnInfo(name = "karting_center_location") var location : String,
    @ColumnInfo(name = "karting_center_visits") var visits : Int
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "karting_center_id")
    var kartingCenterId : Long = 0
}
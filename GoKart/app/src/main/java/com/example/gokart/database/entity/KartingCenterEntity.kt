package com.example.gokart.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "karting_center")
data class KartingCenterEntity(
    @ColumnInfo(name = "karting_center_name") var name : String,
    @ColumnInfo(name = "karting_center_visits") var visits : Int,
    @ColumnInfo(name = "karting_center_layout") var layout : Int
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "karting_center_id")
    var kartingCenterId : Long = 0
}
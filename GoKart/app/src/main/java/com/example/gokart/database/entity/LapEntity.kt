package com.example.gokart.database.entity

import androidx.room.*

@Entity( tableName = "lap" )
data class LapEntity (
    @ColumnInfo( name = "lap_time_sheet" ) var timeSheetId : Long,
    @ColumnInfo( name = "lap_number") var number : Int,
    @ColumnInfo( name = "lap_time" ) var time : String,
    @ColumnInfo( name = "lap_best_delta" ) var bestDelta : String,
    @ColumnInfo( name = "lap_last_delta" ) var lastDelta : String
    ){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lap_id")
    var lapId : Long = 0
}
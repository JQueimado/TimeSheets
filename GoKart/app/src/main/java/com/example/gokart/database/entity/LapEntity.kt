package com.example.gokart.database.entity

import androidx.room.*

@Entity( tableName = "lap" )
class LapEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lap_id")
    val lapId : Int,

    @ColumnInfo( name = "lap_time_sheet" ) val timeSheetId : Int,
    @ColumnInfo( name = "lap_number") val number : Int,
    @ColumnInfo( name = "lap_time" ) val time : String,
    @ColumnInfo( name = "lap_best_delta" ) val bestDelta : String,
    @ColumnInfo( name = "lap_last_delta" ) val lastDelta : String
    )
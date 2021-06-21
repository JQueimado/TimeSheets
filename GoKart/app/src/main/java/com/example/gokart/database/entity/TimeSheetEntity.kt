package com.example.gokart.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity( tableName = "time_sheet" )
data class TimeSheetEntity (
        @PrimaryKey( autoGenerate = true )
        @ColumnInfo(name = "time_sheet_id")
        val timeSheetId : Int,

        @ColumnInfo( name = "time_sheet_kart" ) val kartId : Int,
        @ColumnInfo( name = "time_sheet_karting_center" ) val kartingCenterId : Int,
        @ColumnInfo( name = "time_sheet_best_lap" ) val bestLap : Int,
        @ColumnInfo( name = "time_sheet_worst_lap" ) val worstLap : Int,
        @ColumnInfo( name = "time_sheet_average_lap" ) val averageLap : Int,
        @ColumnInfo( name = "time_sheet_consistency" ) val consistency : Int,
        @ColumnInfo( name = "time_sheet_date" ) val date : Date
        )
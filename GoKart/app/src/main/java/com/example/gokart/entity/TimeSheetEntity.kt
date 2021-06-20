package com.example.gokart.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity( tableName = "time_sheet" )
data class TimeSheetEntity (
        @PrimaryKey val timeSheetId : Int,
        @ColumnInfo( name = "kart_id" ) val kartId : Int,
        @ColumnInfo( name = "karting_center_id" ) val kartingCenterId : Int,
        @ColumnInfo( name = "best_lap" ) val bestLap : Int,
        @ColumnInfo( name = "worst_lap" ) val worstLap : Int,
        @ColumnInfo( name = "average_lap" ) val averageLap : Int,
        @ColumnInfo( name = "consistency" ) val consistency : Int,
        @ColumnInfo( name = "date" ) val date : Date
        )
package com.example.gokart.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity( tableName = "time_sheet" )
data class TimeSheetEntity (
        @ColumnInfo( name = "time_sheet_kart" ) var kartId : Long,
        @ColumnInfo( name = "time_sheet_karting_center" ) var kartingCenterId : Long,
        @ColumnInfo( name = "time_sheet_best_lap" ) var bestLap : Int,
        @ColumnInfo( name = "time_sheet_worst_lap" ) var worstLap : Int,
        @ColumnInfo( name = "time_sheet_average_lap" ) var averageLap : Int,
        @ColumnInfo( name = "time_sheet_consistency" ) var consistency : Int,
        @ColumnInfo( name = "time_sheet_date" ) var date : Long
        ){

        @PrimaryKey( autoGenerate = true )
        @ColumnInfo(name = "time_sheet_id")
        var timeSheetId : Long = 0
}
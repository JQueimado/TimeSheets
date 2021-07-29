package com.example.gokart.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "stats_sheet")
data class StatsEntity(
    @PrimaryKey
    @ColumnInfo(name = "stats_id") val id: Long = 0,
    @ColumnInfo(name = "stats_sheet_best_lap") var bestLap: Int,
    @ColumnInfo(name = "stats_sheet_average_lap_sum") var averageLapSum: Long,
    @ColumnInfo(name = "stats_sheet_average_lap_num") var averageLapNum: Long,
    @ColumnInfo(name = "stats_sheet_consistency_sum") var consistencySum: Long,
    @ColumnInfo(name = "stats_sheet_consistency_num") var consistencyNum: Long,
    @ColumnInfo(name = "stats_sheet_favourite_kart") var favouriteKart: Long,
    @ColumnInfo(name = "stats_sheet_favourite_karting_center") var favouriteKartingCenter: Long,
)
package com.example.gokart.dao

import androidx.room.*
import com.example.gokart.entity.TimeSheetEntity
import com.example.gokart.entity.TimeSheetWithLaps

@Dao
interface TimeSheetDao {

    @Transaction
    @Query("SELECT * FROM time_sheet")
    fun getAll() : List<TimeSheetWithLaps>

    @Transaction
    @Query("SELECT * FROM time_sheet WHERE timeSheetId == (:id)")
    fun getOne( id : Int ) : List<TimeSheetWithLaps>

    @Insert
    fun insertAll(vararg users: TimeSheetEntity)

    @Delete
    fun delete( timeSheetEntity: TimeSheetEntity )

}
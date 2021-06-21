package com.example.gokart.database.dao

import androidx.room.*
import com.example.gokart.database.entity.TimeSheetEntity
import com.example.gokart.database.entity.TimeSheetWithLaps

@Dao
interface TimeSheetDAO {

    @Transaction
    @Query("SELECT * FROM time_sheet")
    fun getAll() : List<TimeSheetWithLaps>

    @Transaction
    @Query("SELECT * FROM time_sheet WHERE time_sheet_id == (:id)")
    fun getOne( vararg id : Int ) : List<TimeSheetWithLaps>

    @Insert
    fun addTimeSheet( vararg timeSheet : TimeSheetEntity )

    @Update
    fun update( vararg timeSheet : TimeSheetEntity )

    @Delete
    fun delete( timeSheetEntity: TimeSheetEntity )

}
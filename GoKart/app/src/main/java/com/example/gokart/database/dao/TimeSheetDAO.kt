package com.example.gokart.database.dao

import androidx.room.*
import com.example.gokart.database.entity.TimeSheetEntity
import com.example.gokart.database.entity.TimeSheetWithLaps

@Dao
interface TimeSheetDAO {

    @Insert
    fun addTimeSheet( vararg timeSheet : TimeSheetEntity )

    @Update
    fun updateTimeSheet( vararg timeSheet : TimeSheetEntity )

    @Delete
    fun deleteTimeSheet( timeSheetEntity: TimeSheetEntity )

    @Transaction
    @Query("SELECT * FROM time_sheet")
    fun getAllComplex() : List<TimeSheetWithLaps>

    @Transaction
    @Query("SELECT * FROM time_sheet WHERE time_sheet_id == (:id)")
    fun getOneComplex( vararg id : Long ) : List<TimeSheetWithLaps>

    @Query("SELECT * FROM time_sheet")
    fun getAllSimple() : List<TimeSheetEntity>

    @Query("SELECT * FROM time_sheet WHERE time_sheet_id == (:id)")
    fun getOneSimple( vararg id : Long ) : List<TimeSheetEntity>

}
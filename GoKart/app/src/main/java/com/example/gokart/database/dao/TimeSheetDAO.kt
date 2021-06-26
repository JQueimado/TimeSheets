package com.example.gokart.database.dao

import androidx.lifecycle.LiveData
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
    fun getAllComplex() : LiveData<List<TimeSheetWithLaps>>

    @Transaction
    @Query("SELECT * FROM time_sheet WHERE time_sheet_id == (:id)")
    fun getOneComplex( vararg id : Long ) : LiveData<TimeSheetWithLaps>

    @Query("SELECT * FROM time_sheet")
    fun getAllSimple() : LiveData<List<TimeSheetEntity>>

    @Query("SELECT * FROM time_sheet WHERE time_sheet_id == (:id)")
    fun getOneSimple( vararg id : Long ) : LiveData<TimeSheetEntity>

}
package com.example.gokart.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gokart.database.entity.TimeSheetEntity
import com.example.gokart.database.entity.TimeSheetWithLaps

@Dao
interface TimeSheetDAO {

    @Insert
    suspend fun addTimeSheet( timeSheet : TimeSheetEntity ) : Long

    @Update
    suspend fun updateTimeSheet( vararg timeSheet : TimeSheetEntity )

    @Delete
    suspend fun deleteTimeSheet( timeSheetEntity: TimeSheetEntity)

    //Async
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

    //Blocking
    @Transaction
    @Query("SELECT * FROM time_sheet WHERE time_sheet_id == (:id)")
    suspend fun getOneComplexBlocking( vararg id : Long ) : TimeSheetWithLaps
}
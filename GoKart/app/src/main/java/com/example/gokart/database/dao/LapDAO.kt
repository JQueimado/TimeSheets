package com.example.gokart.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gokart.database.entity.LapEntity

@Dao
interface LapDAO {
    @Insert
    fun addLap( vararg lap : LapEntity)

    @Update
    fun updateLap( vararg lap : LapEntity)

    @Delete
    fun deleteLap( lap: LapEntity)

    @Query("SELECT * FROM lap")
    fun getAll() : LiveData<List<LapEntity>>

    @Query("SELECT * FROM lap WHERE lap_id == (:id)")
    fun getOne( vararg id : Long ) : LiveData<LapEntity>
}
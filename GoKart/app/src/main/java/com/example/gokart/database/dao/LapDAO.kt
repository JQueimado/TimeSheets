package com.example.gokart.database.dao

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
    fun getAll() : List<LapEntity>

    @Query("SELECT * FROM lap WHERE lap_id == (:id)")
    fun getOne( vararg id : Long ) : List<LapEntity>
}
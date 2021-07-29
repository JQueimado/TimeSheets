package com.example.gokart.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gokart.database.entity.StatsEntity

@Dao
interface StatsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert( statsEntity: StatsEntity )

    @Update
    suspend fun update( statsEntity: StatsEntity )

    //Observable
    @Query(value = "SELECT * FROM stats_sheet")
    fun getStats() : LiveData<List<StatsEntity>>

    //Blocking
    @Query(value = "SELECT * FROM stats_sheet")
    suspend fun getStatsBlocking() : List<StatsEntity>

}
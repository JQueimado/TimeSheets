package com.example.gokart.database.dao

import androidx.room.*
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.KartingCenterWithKarts

@Dao
interface KartingCenterDAO {

    @Insert
    fun addKartingCenter( vararg kartingCenterEntity: KartingCenterEntity)

    @Update
    fun update( vararg kartingCenterEntity: KartingCenterEntity)

    @Delete
    fun delete( vararg kartingCenterEntity: KartingCenterEntity)

    //Get All
    @Query(value = "SELECT * FROM karting_center")
    fun getAllSimple() : List<KartingCenterEntity>

    @Transaction
    @Query(value = "SELECT * FROM karting_center")
    fun getAllComplex() : List<KartingCenterWithKarts>

    //Get One
    @Query(value = "SELECT * FROM karting_center WHERE karting_center_id = (:id)")
    fun getOneSimple( vararg id : Int) : List<KartingCenterEntity>

    @Transaction
    @Query(value = "SELECT * FROM karting_center WHERE karting_center_id = (:id)")
    fun getOneComplex( vararg id : Int) : List<KartingCenterWithKarts>

}
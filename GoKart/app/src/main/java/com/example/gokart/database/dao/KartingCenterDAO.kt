package com.example.gokart.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.KartingCenterWithKarts

@Dao
interface KartingCenterDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addKartingCenter( vararg kartingCenterEntity: KartingCenterEntity)

    @Update
    suspend fun update( vararg kartingCenterEntity: KartingCenterEntity)

    @Delete
    suspend fun delete( vararg kartingCenterEntity: KartingCenterEntity)

    //Get All
    @Query(value = "SELECT * FROM karting_center")
    fun getAllSimple() : LiveData<List<KartingCenterEntity>>

    @Transaction
    @Query(value = "SELECT * FROM karting_center")
    fun getAllComplex() : LiveData<List<KartingCenterWithKarts>>

    //Get One
    @Query(value = "SELECT * FROM karting_center WHERE karting_center_id = (:id)")
    fun getOneSimple( vararg id : Long) : LiveData<KartingCenterEntity>

    @Transaction
    @Query(value = "SELECT * FROM karting_center WHERE karting_center_id = (:id)")
    fun getOneComplex( vararg id : Long) : LiveData<KartingCenterWithKarts>

    //Helper Queries
    @Query(value = "SELECT karting_center_name FROM karting_center")
    fun getAllNames() : LiveData<MutableList<String>>

    @Transaction
    @Query(value = "SELECT * FROM karting_center WHERE karting_center_name = (:name)")
    fun getOneComplexByName( vararg name: String ) : LiveData<KartingCenterWithKarts>

    //Blocking
    @Query("SELECT karting_center_name FROM karting_center WHERE karting_center_id = (:kartingCenterId)")
    suspend fun getNameBlocking( kartingCenterId: Long ): String

    @Query("SELECT karting_center_id FROM karting_center")
    suspend fun getAllIdsBlocking(): List<Long>
}
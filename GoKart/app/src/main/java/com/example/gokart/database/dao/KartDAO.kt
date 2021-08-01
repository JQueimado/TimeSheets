package com.example.gokart.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartWithTimeSheets

@Dao
interface KartDAO {

    @Insert
    suspend fun addKart( vararg kartEntity: KartEntity )

    @Update
    suspend fun update(vararg  kartEntity: KartEntity )

    @Delete
    suspend fun deleteKart( vararg kartEntity: KartEntity )

    //NonBlocking
    @Query("SELECT * FROM kart")
    fun getAllSimple() : LiveData<List<KartEntity>>

    @Query("SELECT * FROM kart WHERE kart_id = (:kartId)")
    fun getOneSimple( kartId : Long ) : LiveData<KartEntity>

    @Transaction
    @Query("SELECT * FROM kart")
    fun getAllComplex() : LiveData<List<KartWithTimeSheets>>

    @Transaction
    @Query("SELECT * FROM kart WHERE kart_id = (:kartId)")
    fun getOneComplex( kartId : Long ) : LiveData<KartWithTimeSheets>

    //Blocking
    @Query("SELECT * FROM kart WHERE kart_id = (:kartId)")
    suspend fun getNameBlocking( kartId: Long ): KartEntity

}
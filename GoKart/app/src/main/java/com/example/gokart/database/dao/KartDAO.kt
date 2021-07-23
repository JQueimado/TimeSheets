package com.example.gokart.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartWithTimeSheets

@Dao
interface KartDAO {

    @Insert
    fun addKart( vararg kartEntity: KartEntity )

    @Update
    fun update(vararg  kartEntity: KartEntity )

    @Delete
    fun deleteKart( vararg kartEntity: KartEntity )

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

}
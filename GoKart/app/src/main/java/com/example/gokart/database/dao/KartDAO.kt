package com.example.gokart.database.dao

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
    fun getAllSimple() : List<KartEntity>

    @Query("SELECT * FROM kart WHERE kart_id = (:kartId)")
    fun getOneSimple( kartId : Long ) : List<KartEntity>

    @Transaction
    @Query("SELECT * FROM kart")
    fun getAllComplex() : List<KartWithTimeSheets>

    @Transaction
    @Query("SELECT * FROM kart WHERE kart_id = (:kartId)")
    fun getOneComplex( kartId : Long ) : List<KartWithTimeSheets>
}
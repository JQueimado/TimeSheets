package com.example.gokart.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.example.gokart.database.entity.KartEntity

@Dao
interface KartDAO {

    @Insert
    fun addKart( vararg kartEntity: KartEntity )

    @Update
    fun update(vararg  kartEntity: KartEntity )



}
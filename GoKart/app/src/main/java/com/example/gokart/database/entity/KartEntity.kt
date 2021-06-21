package com.example.gokart.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kart")
data class KartEntity (
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "kart_id" )
    val kartId : Int,

    @ColumnInfo( name = "kart_karting_center" ) val kartingCenter : Int,
    @ColumnInfo( name = "kart_number" ) val number : Int,
    @ColumnInfo( name = "kart_displacement" ) val displacement : Int,
    @ColumnInfo( name = "kart_name" ) val name : Int
    )
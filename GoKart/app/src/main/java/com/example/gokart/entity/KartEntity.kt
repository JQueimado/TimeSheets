package com.example.gokart.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kart")
data class KartEntity (
    @PrimaryKey( autoGenerate = true ) val kartId : Int,
    @ColumnInfo( name = "kart_number" ) val number : Int,
    @ColumnInfo( name = "kart_displacement" ) val displacement : Int,
    @ColumnInfo( name = "kart_name" ) val name : Int
    )
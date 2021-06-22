package com.example.gokart.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kart")
data class KartEntity (
    @ColumnInfo( name = "kart_karting_center" ) var kartingCenter : Long,
    @ColumnInfo( name = "kart_number" ) var number : Int,
    @ColumnInfo( name = "kart_displacement" ) var displacement : Int,
    @ColumnInfo( name = "kart_name" ) var name : String
    ){
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "kart_id" )
    var kartId : Long = 0
}
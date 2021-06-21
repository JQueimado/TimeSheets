package com.example.gokart.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gokart.database.dao.KartDAO
import com.example.gokart.database.dao.KartingCenterDAO
import com.example.gokart.database.dao.LapDAO
import com.example.gokart.database.dao.TimeSheetDAO
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.LapEntity
import com.example.gokart.database.entity.TimeSheetEntity

@Database( entities = arrayOf(
    LapEntity::class,
    TimeSheetEntity::class,
    KartEntity::class,
    KartingCenterEntity::class
), version = 1 )
abstract class AppDataBase : RoomDatabase() {
    abstract fun lapDao() : LapDAO
    abstract fun timeSheetDao() : TimeSheetDAO
    abstract fun kartDao() : KartDAO
    abstract fun kartingCenterDao() : KartingCenterDAO
}
package com.example.gokart.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gokart.database.dao.KartDAO
import com.example.gokart.database.dao.KartingCenterDAO
import com.example.gokart.database.dao.LapDAO
import com.example.gokart.database.dao.TimeSheetDAO
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.LapEntity
import com.example.gokart.database.entity.TimeSheetEntity
import java.util.*

@Database( entities = [
    LapEntity::class,
    TimeSheetEntity::class,
    KartEntity::class,
    KartingCenterEntity::class],
    version = 1 )
abstract class AppDatabase : RoomDatabase() {
    companion object{ //Static
        private var instance : AppDatabase? = null //instance
        private val sLock = Object() //Sync lock

        fun getMemoryInstance( application : Application) : AppDatabase{
            synchronized(sLock){
                if( instance == null )
                    instance = Room.inMemoryDatabaseBuilder(
                        application.applicationContext,
                        AppDatabase::class.java
                    ).build()
            }
            return instance!!
        }
    }

    abstract fun lapDao() : LapDAO
    abstract fun timeSheetDao() : TimeSheetDAO
    abstract fun kartDao() : KartDAO
    abstract fun kartingCenterDao() : KartingCenterDAO
}
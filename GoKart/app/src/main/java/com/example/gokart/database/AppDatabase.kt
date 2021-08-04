package com.example.gokart.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gokart.database.dao.*
import com.example.gokart.database.entity.*
import java.util.*

@Database( entities = [
    StatsEntity::class,
    LapEntity::class,
    TimeSheetEntity::class,
    KartEntity::class,
    KartingCenterEntity::class],
    version = 2 )
abstract class AppDatabase : RoomDatabase() {
    companion object{ //Static
        private var instance : AppDatabase? = null //instance
        private val sLock = Object() //Sync lock

        fun getInstance(application : Application) : AppDatabase{
            synchronized(sLock){
                if( instance == null )
                    instance = Room.databaseBuilder(
                        application.applicationContext,
                        AppDatabase::class.java,
                        "kartingDB"
                    ).build()
            }
            return instance!!
        }
    }

    abstract fun lapDao() : LapDAO
    abstract fun timeSheetDao() : TimeSheetDAO
    abstract fun kartDao() : KartDAO
    abstract fun kartingCenterDao() : KartingCenterDAO
    abstract fun statsDao(): StatsDao
}
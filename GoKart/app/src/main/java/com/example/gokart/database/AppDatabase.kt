package com.example.gokart.database

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

    private var INSTANCE : AppDatabase? = null
    private val sLock = Object()

    fun getMemoryInstance( context : Context) : AppDatabase{
        synchronized(sLock){
            if( INSTANCE == null )
                INSTANCE = Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java
                ).build()
        }
        return INSTANCE!!
    }

    abstract fun lapDao() : LapDAO
    abstract fun timeSheetDao() : TimeSheetDAO
    abstract fun kartDao() : KartDAO
    abstract fun kartingCenterDao() : KartingCenterDAO
}
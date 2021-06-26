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

    fun getInstance( context : Context, mode : Int) : AppDatabase{
        synchronized(sLock){

            if ( INSTANCE == null ){
                if (mode == AppDatabase.MODE_TEST)
                    INSTANCE = Room.inMemoryDatabaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java)
                        .allowMainThreadQueries()
                        .build()
                else if( mode == MODE_PROD )
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "GoKart.db")
                        .allowMainThreadQueries()
                        .build()
            }
        }
        return INSTANCE!!
    }

    companion object {
        private val MODE_TEST = 0
        private val MODE_PROD = 1
    }

    abstract fun lapDao() : LapDAO
    abstract fun timeSheetDao() : TimeSheetDAO
    abstract fun kartDao() : KartDAO
    abstract fun kartingCenterDao() : KartingCenterDAO
}
package com.example.gokart

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gokart.database.AppDatabase
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseCreateTest {

    lateinit var db : AppDatabase

    @Test
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder( context, AppDatabase::class.java).build()

        assertNotNull(db)

        assertNotNull(db.kartingCenterDao())
        assertNotNull(db.kartDao())
        assertNotNull(db.lapDao())
        assertNotNull(db.timeSheetDao())
    }

    @After
    fun close(){
        db.close()
    }
}
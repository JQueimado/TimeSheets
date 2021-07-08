package com.example.gokart

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.dao.LapDAO
import com.example.gokart.database.entity.LapEntity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseLapTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db : AppDatabase
    private lateinit var lapDAO : LapDAO

    private val testLaps = arrayListOf(
        LapEntity(0,1,"1.30:300", "+122","-0.001"),
        LapEntity(0,2,"1.31:300", "+123","-0.002"),
        LapEntity(0,3,"1.34:300", "+124","-0.003"),
        LapEntity(0,4,"1.36:300", "+125","-0.004")
    )

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder( context, AppDatabase::class.java).build()
        lapDAO = db.lapDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    fun writeReadAllLaps(){
        lapDAO.addLap( testLaps[0] )
        val result = lapDAO.getAll().waitAndGet()[0]
        assertThat( result, equalTo(testLaps[0]) )
    }

    @Test
    fun writeReadOneLap(){
        lapDAO.addLap( testLaps[0] )
        val result1 = lapDAO.getAll().waitAndGet()[0]
        val result2 = lapDAO.getOne( result1.lapId ).waitAndGet()
        assertThat( result2, equalTo(testLaps[0]) )
    }

    @Test
    fun deleteLap(){
        lapDAO.addLap( testLaps[0] )
        val result1 = lapDAO.getAll().waitAndGet()[0]
        lapDAO.deleteLap( result1 )
        val result2 = lapDAO.getAll().waitAndGet()
        assertThat( result2.size, equalTo(0) )
    }

    @Test
    fun updateLap(){
        lapDAO.addLap( testLaps[0] )
        val result1 = lapDAO.getAll().waitAndGet()[0]

        val values = testLaps[1]
        values.lapId = result1.lapId
        lapDAO.updateLap(values)

        val result2 = lapDAO.getOne(result1.lapId).waitAndGet()

        assertThat( result2, equalTo(testLaps[1]) )
    }

}
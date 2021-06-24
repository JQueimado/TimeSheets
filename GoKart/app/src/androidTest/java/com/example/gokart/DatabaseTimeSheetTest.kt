package com.example.gokart

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.dao.TimeSheetDAO
import com.example.gokart.database.entity.TimeSheetEntity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTimeSheetTest {

    private lateinit var db : AppDatabase
    private lateinit var timeSheetDAO : TimeSheetDAO

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder( context, AppDatabase::class.java).build()
        timeSheetDAO = db.timeSheetDao()
    }

    private val testTimeSheets = arrayListOf(
        TimeSheetEntity( 0, 0, 100, 1000, 100,60, 10000),
        TimeSheetEntity( 0, 0, 50, 100, 100,60, 10000),
        TimeSheetEntity( 0, 0, 200, 1000, 100,60, 10000)
    )

    @After
    fun close(){
        db.close()
    }

    //Write Read
    @Test
    fun readWriteTimeSheetTest(){
        timeSheetDAO.addTimeSheet( testTimeSheets[0] ) //Write
        val result = timeSheetDAO.getAllSimple()[0]
        assertThat( result, equalTo(testTimeSheets[0]) )
    }

    //Multi Write Read
    @Test
    fun readWriteMultiple(){
        for (ts in testTimeSheets){
            timeSheetDAO.addTimeSheet(ts)
        }

        val result = timeSheetDAO.getAllSimple()

        for (i in 0 until testTimeSheets.size){
            assertThat( result[i], equalTo(testTimeSheets[i]) )
        }
    }

    //Test Delete
    @Test
    fun deleteTimeSheet(){
        timeSheetDAO.addTimeSheet(testTimeSheets[0])
        val result1 = timeSheetDAO.getAllSimple()[0]
        timeSheetDAO.deleteTimeSheet(result1)
        val result2 = timeSheetDAO.getAllSimple()
        assertThat( result2.size, equalTo(0) ) //check if it was deleted
    }

    @Test
    fun updateTimeSheet(){
        timeSheetDAO.addTimeSheet(testTimeSheets[0])
        val result1 = timeSheetDAO.getAllSimple()[0]

        val values = testTimeSheets[1]
        values.timeSheetId = result1.timeSheetId
        timeSheetDAO.updateTimeSheet( values ) //Update test0 to test1

        val result2 = timeSheetDAO.getOneSimple( result1.timeSheetId )[0] //get updated value
        assertThat( result2, equalTo(testTimeSheets[1]) ) //verify update
    }
}
package com.example.gokart

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.dao.LapDAO
import com.example.gokart.database.dao.TimeSheetDAO
import com.example.gokart.database.entity.LapEntity
import com.example.gokart.database.entity.TimeSheetEntity
import com.example.gokart.database.entity.TimeSheetWithLaps
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DatabaseTimeSheetTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()
    
    private lateinit var db : AppDatabase
    private lateinit var timeSheetDAO : TimeSheetDAO
    private lateinit var lapDAO : LapDAO

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder( context, AppDatabase::class.java).build()
        timeSheetDAO = db.timeSheetDao()
        lapDAO = db.lapDao()
    }

    private val testTimeSheets = arrayListOf(
        TimeSheetEntity( 0, 0, 100, 1000, 100,60, 10000),
        TimeSheetEntity( 0, 0, 50, 100, 100,60, 10000),
        TimeSheetEntity( 0, 0, 200, 1000, 100,60, 10000)
    )

    private val testLaps = arrayListOf(
        LapEntity(0,1,"1.30:300", "+122","-0.001"),
        LapEntity(0,2,"1.31:300", "+123","-0.002"),
        LapEntity(0,3,"1.34:300", "+124","-0.003"),
        LapEntity(0,4,"1.36:300", "+125","-0.004")
    )

    @After
    fun close(){
        db.close()
    }

    //Write Read
    @Test
    fun readWriteTimeSheetTest() = runBlocking{
        timeSheetDAO.addTimeSheet( testTimeSheets[0] ) //Write
        val result = timeSheetDAO.getAllSimple().waitAndGet()[0]
        assertThat( result, equalTo(testTimeSheets[0]) )
    }

    //Multi Write Read
    @Test
    fun readWriteMultiple() = runBlocking{
        for (ts in testTimeSheets){
            timeSheetDAO.addTimeSheet(ts)
        }

        val result = timeSheetDAO.getAllSimple().waitAndGet()

        for (i in 0 until testTimeSheets.size){
            assertThat( result[i], equalTo(testTimeSheets[i]) )
        }
    }

    @Test
    fun readOneTimeSheet() = runBlocking{
        timeSheetDAO.addTimeSheet( testTimeSheets[0] ) //Write
        val result1 = timeSheetDAO.getAllSimple().waitAndGet()[0]
        val result2 = timeSheetDAO.getOneSimple( result1.timeSheetId ).waitAndGet()
        assertThat( result2, equalTo(result1) )
    }

    //Test Delete
    @Test
    fun deleteTimeSheet() = runBlocking{
        timeSheetDAO.addTimeSheet(testTimeSheets[0])
        val result1 = timeSheetDAO.getAllSimple().waitAndGet()[0]
        timeSheetDAO.deleteTimeSheet(result1)
        val result2 = timeSheetDAO.getAllSimple().waitAndGet()
        assertThat( result2.size, equalTo(0) ) //check if it was deleted
    }

    @Test
    fun updateTimeSheet() = runBlocking{
        timeSheetDAO.addTimeSheet(testTimeSheets[0])
        val result1 = timeSheetDAO.getAllSimple().waitAndGet()[0]

        val values = testTimeSheets[1]
        values.timeSheetId = result1.timeSheetId
        timeSheetDAO.updateTimeSheet( values ) //Update test0 to test1

        val result2 = timeSheetDAO.getOneSimple( result1.timeSheetId ).waitAndGet() //get updated value
        assertThat( result2, equalTo(testTimeSheets[1]) ) //verify update
    }

    @Test
    fun getAllComplexTimeSheet() = runBlocking{
        timeSheetDAO.addTimeSheet(testTimeSheets[0])
        val result1 = timeSheetDAO.getAllSimple().waitAndGet()[0]

        for (lap in testLaps){
            lap.timeSheetId = result1.timeSheetId
            lapDAO.addLap(lap)
        }

        val testCompare = TimeSheetWithLaps( result1, testLaps )
        val result = timeSheetDAO.getAllComplex().waitAndGet()[0]
        assertThat( result, equalTo(testCompare) )
    }

    @Test
    fun getOneComplexTimeSheet() = runBlocking{
        timeSheetDAO.addTimeSheet(testTimeSheets[0])
        val result1 = timeSheetDAO.getAllSimple().waitAndGet()[0]

        for (lap in testLaps){
            lap.timeSheetId = result1.timeSheetId
            lapDAO.addLap(lap)
        }

        val testCompare = TimeSheetWithLaps( result1, testLaps )
        val result = timeSheetDAO.getOneComplex( result1.timeSheetId ).waitAndGet()
        assertThat( result, equalTo(testCompare) )
    }
}
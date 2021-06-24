package com.example.gokart

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.dao.KartDAO
import com.example.gokart.database.dao.TimeSheetDAO
import org.hamcrest.CoreMatchers.equalTo
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartWithTimeSheets
import com.example.gokart.database.entity.TimeSheetEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseKartTest {

    private lateinit var db : AppDatabase
    private lateinit var kartDAO: KartDAO
    private lateinit var timeSheetDAO: TimeSheetDAO

    private val testKarts = arrayListOf(
        KartEntity(1L,23,50, ""),
        KartEntity(0L,50,50, ""),
        KartEntity(1L,23,125, ""),
        KartEntity(0L,420,125, "Some Name"),
    )

    private val testTimeSheets = arrayListOf(
        TimeSheetEntity( 0, 0, 100, 1000, 100,60, 10000),
        TimeSheetEntity( 0, 0, 50, 100, 100,60, 10000),
        TimeSheetEntity( 0, 0, 200, 1000, 100,60, 10000)
    )

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder( context, AppDatabase::class.java).build()
        kartDAO = db.kartDao()
        timeSheetDAO = db.timeSheetDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    //Insert Read
    @Test
    fun writeReadKart() {
        kartDAO.addKart(testKarts[0])

        val result = kartDAO.getAllSimple()[0]
        assertThat( result, equalTo(testKarts[0]) )
    }

    //multi read write
    @Test
    fun multiWriteReadKart() {
        for (kart in testKarts )
            kartDAO.addKart(kart)

        val result = kartDAO.getAllSimple()
        for (kart_n in 0 until testKarts.size) {
            val kartOut = result[kart_n]
            assertThat(kartOut, equalTo(testKarts[kart_n]))
        }
    }

    @Test
    fun readOneKart(){
        kartDAO.addKart( testKarts[0] ) //write
        val result1 = kartDAO.getAllSimple()[0] //read all
        val result2 = kartDAO.getOneSimple(result1.kartId)[0] //read one
        assertThat( result1, equalTo(result2) ) //compare
    }

    //Delete
    @Test
    fun deleteKart() {
        kartDAO.addKart(testKarts[0])
        val kart = kartDAO.getAllSimple()[0]
        kartDAO.deleteKart(kart)
        assertThat( kartDAO.getAllSimple().size, equalTo(0) )
    }

    //Update
    @Test
    fun updateKart() {
        kartDAO.addKart(testKarts[0])
        val kart = kartDAO.getAllSimple()[0]
        val kartValues = testKarts[3]
        kart.name = kartValues.name
        kart.displacement = kartValues.displacement
        kart.number = kartValues.number
        kart.kartingCenter = kartValues.kartingCenter

        kartDAO.update(kart)
        assertThat( kartDAO.getAllSimple()[0], equalTo(testKarts[3]) )
    }

    //ComplexTest
    @Test
    fun readAllComplex(){
        kartDAO.addKart(testKarts[0])
        val result1 = kartDAO.getAllSimple()[0]

        for( ts in testTimeSheets ){
            ts.kartId = result1.kartId
            timeSheetDAO.addTimeSheet(ts)
        }

        val compare = KartWithTimeSheets( result1, testTimeSheets)
        val result2 = kartDAO.getAllComplex()[0]
        assertThat( result2, equalTo(compare) )
    }

    @Test
    fun readOneComplex(){
        kartDAO.addKart(testKarts[0])
        val result1 = kartDAO.getAllSimple()[0]

        for( ts in testTimeSheets ){
            ts.kartId = result1.kartId
            timeSheetDAO.addTimeSheet(ts)
        }

        val compare = KartWithTimeSheets( result1, testTimeSheets)
        val result2 = kartDAO.getOneComplex(result1.kartId)[0]
        assertThat( result2, equalTo(compare) )
    }
}
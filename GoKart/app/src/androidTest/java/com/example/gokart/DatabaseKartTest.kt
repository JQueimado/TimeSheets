package com.example.gokart

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.dao.KartDAO
import org.hamcrest.CoreMatchers.equalTo
import com.example.gokart.database.entity.KartEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseKartTest {

    private lateinit var db : AppDatabase
    private lateinit var kartDAO: KartDAO
    private val testKarts = arrayListOf(
        KartEntity(1L,23,50, ""),
        KartEntity(0L,50,50, ""),
        KartEntity(1L,23,125, ""),
        KartEntity(0L,420,125, "Some Name"),
    )

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder( context, AppDatabase::class.java).build()
        kartDAO = db.kartDao()
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
}
package com.example.gokart

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.dao.KartDAO
import com.example.gokart.database.dao.KartingCenterDAO
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.KartingCenterWithKarts
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseKartingCenterTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db : AppDatabase
    private lateinit var kartingCenterDAO: KartingCenterDAO
    private lateinit var kartDAO: KartDAO

    private val testKartingCenters = arrayListOf(
        KartingCenterEntity("1234", "321", 0),
        KartingCenterEntity("4321", "321", 0),
        KartingCenterEntity("5432", "321", 0)
    )

    private val testKarts = arrayListOf(
        KartEntity(0,23, 50, ""),
        KartEntity(0, 30, 50, ""),
        KartEntity(0, 50, 125, "")
    )

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder( context, AppDatabase::class.java).build()

        kartingCenterDAO = db.kartingCenterDao()
        kartDAO = db.kartDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    //Write Read
    @Test
    fun readWriteKartingCenter(){
        val kartingCenter1 = testKartingCenters[0]
        kartingCenterDAO.addKartingCenter(kartingCenter1)

        val kartingCenter2 : KartingCenterEntity = kartingCenterDAO.getAllSimple().waitAndGet()[0]
        assertThat( kartingCenter2, equalTo(kartingCenter1) ) //Compare inserted value with extracted value
    }

    //Update
    @Test
    fun updateKartingCenter(){
        //add karting center
        var kartingCenter1 = testKartingCenters[0]
        kartingCenterDAO.addKartingCenter(kartingCenter1)

        //read Value
        kartingCenter1 = kartingCenterDAO.getAllSimple().waitAndGet()[0]

        val kartinCenterValues = testKartingCenters[1]

        //update
        kartingCenter1.name = kartinCenterValues.name
        kartingCenter1.location = kartinCenterValues.location
        kartingCenterDAO.update(kartingCenter1)

        //check update
        val result = kartingCenterDAO.getAllSimple()
        assertThat( result.waitAndGet()[0].kartingCenterId, equalTo(kartingCenter1.kartingCenterId) )
        assertThat( result.waitAndGet()[0], equalTo(testKartingCenters[1]) ) //Compare values
    }

    //Delete Test
    @Test
    fun deleteKartingCenter(){
        //add karting center
        var kartingCenter1 = testKartingCenters[0]
        kartingCenterDAO.addKartingCenter(kartingCenter1)

        //read Value
        kartingCenter1 = kartingCenterDAO.getAllSimple().waitAndGet()[0]

        //delete
        kartingCenterDAO.delete(kartingCenter1)
        val result = kartingCenterDAO.getAllSimple().waitAndGet()

        //Test
        assertThat( result.size, equalTo(0) ) //Clean result
    }

    //Get One Simple
    @Test
    fun getOneKartingCenter(){
        //add karting center
        var kartingCenter1 = testKartingCenters[0]
        kartingCenterDAO.addKartingCenter(kartingCenter1)

        //read Value
        kartingCenter1 = kartingCenterDAO.getAllSimple().waitAndGet()[0]
        val kartingCenter2 = kartingCenterDAO
            .getOneSimple(kartingCenter1.kartingCenterId)
            .waitAndGet()

        //Test
        assertThat( kartingCenter2, equalTo(kartingCenter1) ) //Clean result
    }

    //Test multiple values
    @Test
    fun readWriteMultipleKartingCenter(){
        for (kartingCenter in testKartingCenters){
            kartingCenterDAO.addKartingCenter(kartingCenter)
        }

        val list = kartingCenterDAO.getAllSimple().waitAndGet()
        assertThat( list.size, equalTo(testKartingCenters.size) ) //Compare sizes

        for ( kartingCenter_n in 0 until testKartingCenters.size )
            assertThat( list[kartingCenter_n], equalTo(testKartingCenters[kartingCenter_n]) )

    }

    //test relation extraction
    @Test
    fun readwriteComplex(){
        kartingCenterDAO.addKartingCenter(testKartingCenters[0])

        val result = kartingCenterDAO.getAllSimple().waitAndGet()
        val kartingCenter1 = result[0]

        for (kart in testKarts){
            kart.kartingCenter = kartingCenter1.kartingCenterId
            kartDAO.addKart(kart)
        }

        val compare = KartingCenterWithKarts( kartingCenter1, testKarts )

        val result2 = kartingCenterDAO.getAllComplex().waitAndGet()[0]
        assertThat( result2, equalTo(compare) )
    }

    //test relation extraction
    @Test
    fun readOneComplex(){
        //insert
        kartingCenterDAO.addKartingCenter(testKartingCenters[0])

        val result = kartingCenterDAO.getAllSimple().waitAndGet()
        val kartingCenter1 = result[0]

        for (kart in testKarts){
            kart.kartingCenter = kartingCenter1.kartingCenterId
            kartDAO.addKart(kart)
        }

        val result2 = kartingCenterDAO.getAllSimple().waitAndGet()[0]
        val result3 = kartingCenterDAO.getOneComplex(result2.kartingCenterId).waitAndGet()
        val kartList = kartDAO.getAllSimple().waitAndGet()

        assertThat( result3.karts, equalTo(kartList) )

    }
}
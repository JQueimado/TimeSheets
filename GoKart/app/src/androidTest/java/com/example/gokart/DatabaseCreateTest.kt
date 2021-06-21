package com.example.gokart

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.dao.KartDAO
import com.example.gokart.database.dao.KartingCenterDAO
import com.example.gokart.database.entity.KartingCenterEntity
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseCreateTest {

    private lateinit var db : AppDatabase

    private lateinit var kartingCenterDAO: KartingCenterDAO
    private lateinit var kartDAO: KartDAO

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder( context, AppDatabase::class.java).build()

        kartingCenterDAO = db.kartingCenterDao()
        kartDAO = db.kartDao()

        assertNotNull(db)

        assertNotNull(kartingCenterDAO)
        assertNotNull(kartDAO)
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }


    //Write Read Karting Center
    @Test
    fun readWriteKartingCenter(){
        val kartingCenter1 = KartingCenterEntity("1234", "321", 0)
        kartingCenterDAO.addKartingCenter(kartingCenter1)

        val kartingCenter2 : KartingCenterEntity = kartingCenterDAO.getAllSimple()[0]
        assertThat( kartingCenter2, equalTo(kartingCenter1) ) //Compare inserted value with extracted value
    }

    //Update Karting Center
    @Test
    fun updateKartingCenter(){
        //add karting center
        var kartingCenter1 = KartingCenterEntity("1234", "321", 0)
        kartingCenterDAO.addKartingCenter(kartingCenter1)

        //read Value
        kartingCenter1 = kartingCenterDAO.getAllSimple()[0]

        //update
        kartingCenter1.name = "4321"
        kartingCenter1.location = "123"
        kartingCenterDAO.update(kartingCenter1)

        //check update
        var result = kartingCenterDAO.getAllSimple()
        assertThat( result[0].kartingCenterId, equalTo(kartingCenter1.kartingCenterId) )
        assertThat( result[0], equalTo(kartingCenter1) ) //Compare values
    }

}
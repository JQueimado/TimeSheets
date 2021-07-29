package com.example.gokart

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gokart.database.AppDatabase
import com.example.gokart.database.dao.LapDAO
import com.example.gokart.database.dao.StatsDao
import com.example.gokart.database.entity.StatsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DatabaseStatsTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var db : AppDatabase
    private lateinit var statsDao : StatsDao

    private val statsId = 0L
    private val testStats = arrayListOf<StatsEntity>(
        StatsEntity(
            statsId,
            11,
            123,
            1,1,
            0,
            1,
            1
        ),
        StatsEntity(
            statsId,
            12,
            124,
            2,
            2,
            1,
            2,
            1
        )
    )

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder( context, AppDatabase::class.java).build()
        statsDao = db.statsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    fun writeReadTest() = runBlocking {
        statsDao.insert( testStats[0] )
        val result = statsDao.getStats(statsId).waitAndGet()
        assertThat( result, equalTo(testStats[0]) )
    }

    @Test
    fun updateTest() = runBlocking {
        statsDao.insert(testStats[0])
        val values = testStats[1]
        statsDao.update(values)
        val result = statsDao.getStats(statsId).waitAndGet()
        assertThat( result, equalTo(values) )
    }
}
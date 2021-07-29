package com.example.gokart.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.add_activity.AddActivity
import com.example.gokart.R
import com.example.gokart.database.entity.LapEntity
import com.example.gokart.database.entity.TimeSheetEntity
import com.example.gokart.database.entity.TimeSheetWithLaps
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // Demo mode 0 -> Ui demo
    // Demo mode 1 -> In memory Database (test)
    private val mode = 1

    private val mainActivityDatabaseViewModel : MainActivityDatabaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set Variables
        val contentList : MutableList<TimeSheetWithLaps> = ArrayList()
        val myRVAdapter = TimeSheetsRVAdapter(this, contentList)
        val layoutManager = LinearLayoutManager( this )
        val recyclerView : RecyclerView = findViewById(R.id.main_activity_scroll_content)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = myRVAdapter

        if ( mode == 0) { /* TestMode */

            //Times Demo
            for (i in 1..1000) {
                val timeSheetWithLaps = TimeSheetWithLaps(
                    TimeSheetEntity(
                        0,
                        0,
                        78348,
                        79659,
                        79659,
                        60,
                        Date().time),
                    arrayListOf(
                        LapEntity(
                            0,
                            0,
                            "1.18.348",
                            "+1.111",
                            "-1.111"),
                        LapEntity(
                            0,
                            0,
                            "1.18.348",
                            "+1.111",
                            "-1.111"),
                        LapEntity(
                            0,
                            0,
                            "1.18.348",
                            "+1.111",
                            "-1.111")
                    )
                )
                contentList.add(timeSheetWithLaps)
            }

            myRVAdapter.setData(contentList)

        }else if( mode == 1){ /* Application mode */
            //Data Observer
            mainActivityDatabaseViewModel.getTimeSheets().observe( this, {
                //Update
                myRVAdapter.setData( it )
            } )

            mainActivityDatabaseViewModel.getStats().observe(this, {
                if (it.isNotEmpty())
                    myRVAdapter.setStats(it[0])
            })
        }

        //Add button action
        val addButton :Button = findViewById(R.id.nav_add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }

    fun getViewModel() : MainActivityDatabaseViewModel{
        return mainActivityDatabaseViewModel
    }
}
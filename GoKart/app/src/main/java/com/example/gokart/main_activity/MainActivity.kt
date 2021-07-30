package com.example.gokart.main_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.R
import com.example.gokart.add_activity.AddActivity
import com.example.gokart.database.entity.*
import com.example.gokart.main_activity.TimeSheetsRVAdapter.TimeSheetActionFunction
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainActivityDatabaseViewModel : MainActivityDatabaseViewModel by viewModels()

    //Interactions
    private val timeSheetActions = object : TimeSheetActionFunction {
        override fun onDeleteAction(timeSheet: TimeSheetEntity) {
            mainActivityDatabaseViewModel.getTimeSheets().removeObservers(this@MainActivity)
            mainActivityDatabaseViewModel.deleteTimeSheet(timeSheet.timeSheetId) {
                mainActivityDatabaseViewModel.getTimeSheets()
                    .observe(this@MainActivity, timeSheetObserver)
            }
        }

        override fun onEditAction(timeSheet: TimeSheetEntity) {
            TODO("Not yet implemented")
        }
    }

    private val viewModelAccess = object: TimeSheetsRVAdapter.ViewModelAccess{
        override fun getKartById(id: Long): LiveData<KartEntity> {
            return mainActivityDatabaseViewModel.getKart(id)
        }

        override fun getKartingCenterById(id: Long): LiveData<KartingCenterEntity> {
            return mainActivityDatabaseViewModel.getKartingCenter(id)
        }
    }

    //RV Adapter
    private val myRVAdapter = TimeSheetsRVAdapter(this, timeSheetActions, viewModelAccess)

    //Observers
    private val timeSheetObserver: Observer<List<TimeSheetWithLaps>> = Observer {
        myRVAdapter.setData( it )
        Log.d("database observer", "Updating TimeSheets 4 ${it.size}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Values
        val layoutManager = LinearLayoutManager( this )
        val recyclerView : RecyclerView = findViewById(R.id.main_activity_scroll_content)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = myRVAdapter

        //Data Observer
        mainActivityDatabaseViewModel.getTimeSheets().observe( this, timeSheetObserver )

        mainActivityDatabaseViewModel.getStats().observe(this, {
            if (it.isNotEmpty()) {
                myRVAdapter.setStats(it[0])
            }
        })

        //Add button action
        val addButton :Button = findViewById(R.id.nav_add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }
}
package com.example.gokart.main_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
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
import com.example.gokart.time_sheet_activity.TimeSheetActivity
import com.example.gokart.time_sheet_activity.TimeSheetView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainActivityDatabaseViewModel : MainActivityDatabaseViewModel by viewModels()
    private val statsViewModel: StatsViewModel by viewModels()

    //Interactions
    private val timeSheetActions = object : TimeSheetActionFunction {
        override fun onDeleteAction(timeSheet: TimeSheetEntity) {
            mainActivityDatabaseViewModel.deleteTimeSheet(timeSheet.timeSheetId)
        }

        override fun onEditAction(timeSheet: TimeSheetEntity) {
            TODO("Not yet implemented")
        }

        override fun onViewTimeSheet(timeSheet: TimeSheetEntity) {
            val intent = Intent(this@MainActivity, TimeSheetActivity::class.java)
            intent.putExtra( TimeSheetActivity.TIME_SHEET_ID, timeSheet.timeSheetId )
            startActivity( intent )
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
    private val timeSheetObserver: Observer<List<TimeSheetEntity>> = Observer {
        myRVAdapter.submitList(it)
        Log.d("database observer", "Updating TimeSheets 4 ${it.size}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Values
        val layoutManager = LinearLayoutManager( this )
        val recyclerView : RecyclerView = findViewById(R.id.main_activity_scroll_content)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = myRVAdapter

        val statsView = StatsView(this)
        findViewById<LinearLayout>(R.id.items_linear_layout).addView(statsView, 0)

        //Data Observer
        mainActivityDatabaseViewModel.getTimeSheets().observe( this, timeSheetObserver )

        mainActivityDatabaseViewModel.getStats().observe(this, {
            if (it.isNotEmpty()) {
                statsViewModel.setStatsToView( it[0], statsView )
            }
        })

        //Add button action
        val addButton: Button = findViewById(R.id.nav_add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }
}
package com.example.gokart.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    private val DEMO_MODE = 1

    private val completeTimeSheetViewModel : CompleteTimeSheetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {

            if (DEMO_MODE == 0) {

                val contentList : MutableList<TimeSheet> = ArrayList() //Demo1

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

                    val timeSheet = TimeSheet(timeSheetWithLaps)
                    contentList.add(timeSheet)

                    val myRVAdapter = TimeSheetsRVAdapter(this, contentList)
                    val recyclerView : RecyclerView = findViewById(R.id.main_activity_scroll_content)
                    val layoutManager = LinearLayoutManager( this )

                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = myRVAdapter

                }
            }else if(DEMO_MODE == 1){

                val contentList : MutableList<TimeSheet> = ArrayList() //Demo1
                val myRVAdapter = TimeSheetsRVAdapter(this, contentList)

                completeTimeSheetViewModel.get().observe( this, {
                    //Adapt
                    val temp : MutableList<TimeSheet> = ArrayList()
                    for( timeSheet in it )
                        temp.add( TimeSheet(timeSheet) )

                    //Update
                    myRVAdapter.setTimeSheets( temp )
                } )

                val recyclerView : RecyclerView = findViewById(R.id.main_activity_scroll_content)
                val layoutManager = LinearLayoutManager( this )

                layoutManager.orientation = LinearLayoutManager.VERTICAL
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = myRVAdapter

            }

            val addButton :Button = findViewById(R.id.nav_add_button)

            addButton.setOnClickListener( View.OnClickListener {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            } )
        }

    }
}
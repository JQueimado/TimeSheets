package com.example.gokart.time_sheet_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.viewModels
import com.example.gokart.R

class TimeSheetActivity : AppCompatActivity(R.layout.activity_time_sheet) {

    //View Models
    private val databaseViewModel: TimeSheetActivityDatabaseViewModel by viewModels()

    companion object{

        const val TIME_SHEET_ID = "TIME_SHEET_ID"
        private const val TIME_SHEET_ID_DEFAULT_VALUE = -1L

        private const val backButtonId = R.id.nav_back_button

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timeSheetId = intent.getLongExtra( TIME_SHEET_ID, TIME_SHEET_ID_DEFAULT_VALUE )

        if( timeSheetId != TIME_SHEET_ID_DEFAULT_VALUE ) {

            //Create timeSheet view
            val timeSheetView = TimeSheetView( this, object: TimeSheetView.DatabaseAccessInterface{

                override fun setKartingCenter(kartingCenterView: TextView, kartingCenterId: Long) {
                    databaseViewModel.getKartingCenter(kartingCenterId).observe(this@TimeSheetActivity, {
                        kartingCenterView.text = it.name
                    })
                }

                override fun setKart(kartView: TextView, kartId: Long) {
                    databaseViewModel.getKart(kartId).observe(this@TimeSheetActivity, {
                        kartView.text = if( it.name.isBlank() )
                            "${it.number}-${it.displacement}cc"
                        else
                            it.name
                    })
                }

            })
            findViewById<ScrollView>(R.id.time_sheet_activity_body).addView( timeSheetView )

            //time Sheet Observer
            databaseViewModel.getTimeSheet(timeSheetId).observe(this, {
                timeSheetView.setValues(it)
            })
        }

        //Back
        findViewById<Button>(backButtonId).setOnClickListener {
            finish()
        }

    }
}
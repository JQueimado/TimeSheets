package com.example.gokart.time_sheet_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.gokart.R

class TimeSheetActivity : AppCompatActivity(R.layout.activity_time_sheet) {

    companion object{
        private const val backButtonId = R.id.nav_back_button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Back
        findViewById<Button>(backButtonId).setOnClickListener {
            finish()
        }

    }
}
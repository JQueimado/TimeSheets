package com.example.gokart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Time
import android.widget.LinearLayout
import androidx.fragment.app.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<StatsFragment>(R.id.stats_fragment_container_view)
                add<TimeSheetFragment>(R.id.times_sheet_fragment_container_view)
            }
        }

        val timesheetlayout : LinearLayout = findViewById(R.id.times_sheet_list_container)

    }
}
package com.example.gokart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Time
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            /*
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<StatsFragment>(R.id.stats_fragment_container_view)
                //add<TimeSheetFragment>(R.id.times_sheet_fragment_container_view)
                /*
                for (i in 1..10){
                    add(R.id.times_sheet_list_container, TimeSheetFragment())
                }
                */
            }
            */

            val list_view : LinearLayout = findViewById(R.id.main_activity_scroll_content)

            //Stats Demo
            val statsView = StatsView( applicationContext, layoutInflater )
            list_view.addView( statsView )
            statsView.inflate()

            //Times Demo
            for ( i in 1..10){
                val view = TimeSheetView( applicationContext, layoutInflater )
                list_view.addView(view)
                view.inflate()
            }

            val add_button :Button = findViewById(R.id.nav_add_button)

            add_button.setOnClickListener( View.OnClickListener {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            } )
        }

    }
}
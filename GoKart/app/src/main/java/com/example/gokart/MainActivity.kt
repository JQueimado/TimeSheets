package com.example.gokart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {

            val contentList : MutableList<TimeSheet> = ArrayList()

            //Times Demo
            for ( i in 1..10){
                val timeSheet = TimeSheet()
                timeSheet.addDefRow()
                contentList.add(timeSheet)
            }

            //Add to view
            val recyclerView : RecyclerView = findViewById(R.id.main_activity_scroll_content)
            val myRVAdapter = MyRVAdapter(this, contentList)
            val layoutManager = LinearLayoutManager( this )

            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = myRVAdapter

            val add_button :Button = findViewById(R.id.nav_add_button)

            add_button.setOnClickListener( View.OnClickListener {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            } )
        }

    }
}
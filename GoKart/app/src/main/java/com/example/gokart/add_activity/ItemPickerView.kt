package com.example.gokart.add_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.R
import kotlin.random.Random

class ItemPickerView(private val parent : AddActivity) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(
            R.layout.fragment_picker,
            container,
            false)

        //Back Button
        view.findViewById<Button>(R.id.choose_kart_back_button).setOnClickListener(
            View.OnClickListener {
                //TODO
        })

        //On Confirm click
        view.findViewById<Button>(R.id.choose_kart_confirm_button).setOnClickListener(
            View.OnClickListener {
                //TODO
        })


        val namesList : MutableList<String> = ArrayList()

        //Assemble list
        for (i in 1..20) {

            namesList.add( Random.nextInt(0,100).toString() + "-50cc" )
        }

        //Recycler View
        val rvAdapter = PickerRVAdapter(parent.application,namesList)
        val recyclerView : RecyclerView = view.findViewById(R.id.picker_recycler_view)
        val layouManager = LinearLayoutManager(parent.applicationContext)

        layouManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layouManager
        recyclerView.adapter = rvAdapter
        return view
    }

    companion object{
        val KART_MODE : Short = 0
        val KARTING_CENTER_MODE : Short = 1
    }

}
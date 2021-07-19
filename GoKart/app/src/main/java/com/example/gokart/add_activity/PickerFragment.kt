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

class PickerFragment(
    private val parent : AddActivity,
    private val items : MutableList<String>,
    private val mode: Short
    ) : Fragment() {

    //Modes
    companion object{
        const val KART_MODE : Short = 0
        const val KARTING_CENTER_MODE : Short = 1
    }

    //On Ok click
    private val onOKClickKartMode = View.OnClickListener{
        //Confirmation for karts
        if ( mode == KART_MODE) {
            val index =
                rvAdapter.selectedID //cals adapter because is the one that manages selection
            if (index == -1)
                parent.onPickKartConfirm(
                    resources.getString(R.string.add_pick_kart)
                ) //Don't do nothing
            else
                parent.onPickKartConfirm(this.items[index])
            return@OnClickListener
        }

        //Confirmation for karting centers
        if (mode == KARTING_CENTER_MODE){
            val index =
                rvAdapter.selectedID //cals adapter because is the one that manages selection
            if (index == -1)
                parent.onPickKartingCenterConfirm(
                    resources.getString(R.string.add_pick_kart)
                ) //Don't do nothing
            else
                parent.onPickKartingCenterConfirm(this.items[index])
            return@OnClickListener
        }
    }

    //RV
    private lateinit var rvAdapter : PickerRVAdapter
    private lateinit var layoutManager : LinearLayoutManager

    //UI
    private lateinit var okButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Recycler View
        rvAdapter = PickerRVAdapter(parent.application ,items)
        rvAdapter.setItems(items)
        layoutManager = LinearLayoutManager(parent.applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

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

        //Recycler View
        val recyclerView : RecyclerView = view.findViewById(R.id.picker_recycler_view)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = rvAdapter

        //On "OK" click
        okButton = view.findViewById<Button>(R.id.choose_kart_confirm_button)
        okButton.setOnClickListener( onOKClickKartMode )

        return view
    }

    fun setData( items : MutableList<String> ){
        rvAdapter.setItems(items)
    }

}
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
    val activity : AddActivity,
    private val items : MutableList<String>,
    private val mode: Short
    ) : Fragment() {

    //Modes
    companion object{
        const val KART_MODE : Short = 0
        const val KARTING_CENTER_MODE : Short = 1
    }

    //Values
    var selectedID = -1

    //On Ok click
    private lateinit var onOKClick : View.OnClickListener

    //RV
    private lateinit var rvAdapter : PickerRVAdapter

    //UI
    private lateinit var okButton : Button

    //Constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if( savedInstanceState == null ) {
            //Recycler View
            rvAdapter = PickerRVAdapter(this, items, mode)
            rvAdapter.setItems(items)

            //listeners
            onOKClick = View.OnClickListener{
                //Confirmation for karts
                if ( mode == KART_MODE) {
                    val index =
                        selectedID //cals adapter because is the one that manages selection
                    if (index == -1)
                        activity.onPickKartConfirm(
                            resources.getString(R.string.add_pick_kart)
                        ) //Don't do nothing
                    else
                        activity.onPickKartConfirm(this.items[index])
                    return@OnClickListener
                }

                //Confirmation for karting centers
                if (mode == KARTING_CENTER_MODE){
                    val index = selectedID
                    if (index == -1)
                        activity.onPickKartingCenterConfirm(
                            resources.getString(R.string.add_pick_kart_center)
                        ) //Don't do nothing
                    else
                        activity.onPickKartingCenterConfirm(this.items[index])
                    return@OnClickListener
                }
            }
        }
    }

    //Display
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(
            R.layout.fragment_picker,
            container,
            false
        )

        if(savedInstanceState == null) {
            //Recycler View
            val recyclerView: RecyclerView = view.findViewById(R.id.picker_recycler_view)

            val layoutManager = LinearLayoutManager(activity.applicationContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = rvAdapter

            //On "OK" click
            okButton = view.findViewById(R.id.choose_kart_confirm_button)
            okButton.setOnClickListener(onOKClick)

        }

        return view
    }

    //Save state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected", selectedID)
    }

}
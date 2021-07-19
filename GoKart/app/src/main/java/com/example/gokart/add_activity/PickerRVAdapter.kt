package com.example.gokart.add_activity

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.gokart.R

class PickerRVAdapter(application: Application, private var items : MutableList<String>)
    : RecyclerView.Adapter<PickerRVAdapter.PickerVHolder>() {

    //Values
    private val addPosition = 0
    private val inflater = LayoutInflater.from(application.applicationContext)

    private var lastPicked : ConstraintLayout? = null
    var selectedID = -1

    //Create Views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickerVHolder {
        return if (viewType == addPosition){
            PickerVHolder(
                inflater.inflate(R.layout.view_add_picker, parent, false)
            )
        }else{
            PickerVHolder(
                inflater.inflate(R.layout.view_picker_item, parent, false)
            )
        }
    }

    //Assign Values
    @SuppressLint("CutPasteId")
    override fun onBindViewHolder(holder: PickerVHolder, position: Int) {
        if (position != addPosition) {
            //Set Text
            holder.itemView.findViewById<TextView>(R.id.picker_item_label).text = items[position]

            //Create on click listener for item selection
            val onViewClick = View.OnClickListener {

                if(lastPicked == it || lastPicked == null){ //On selected same item
                    val state = it.findViewById<CheckBox>(R.id.picker_item_check_box).isChecked
                    it.findViewById<CheckBox>(R.id.picker_item_check_box).isChecked = !state

                    //Set current selected item (-1 if unselected )
                    selectedID = if( !state )
                        position
                    else
                        -1

                }else{ //On select other item
                    lastPicked!!.findViewById<CheckBox>(R.id.picker_item_check_box).isChecked = false
                    it.findViewById<CheckBox>(R.id.picker_item_check_box).isChecked = true
                    selectedID = position //Set current selected item (redundant for error masking)
                }
                lastPicked = it as ConstraintLayout? //Set previous selected item
            }

            //Set onclick for each item
            holder.itemView.setOnClickListener( onViewClick )
        }
    }

    //Companion Functions
    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setItems( items : MutableList<String> ){
        this.items = items
        notifyDataSetChanged()
    }

    fun clean(){
        if(lastPicked != null)
            lastPicked!!.findViewById<CheckBox>(R.id.picker_item_check_box).isChecked = false

        lastPicked = null
        selectedID = -1
    }

    fun select( position: Int ){

    }

    //Holder
    class PickerVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
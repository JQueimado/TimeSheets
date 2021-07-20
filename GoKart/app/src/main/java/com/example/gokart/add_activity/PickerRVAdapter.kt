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
import com.example.gokart.add_activity.PickerFragment.Companion.KART_MODE

class PickerRVAdapter(
        private val activity: AddActivity,
        private var items : MutableList<String>,
        private val mode : Short
    )
    : RecyclerView.Adapter<PickerRVAdapter.PickerVHolder>() {

    //Values
    private val addPosition = 0
    private val inflater = LayoutInflater.from(activity.application.applicationContext)

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
        }else{
            //Assign text based on selected mode
            if( mode == KART_MODE ) {
                //Text
                val addButton = holder.itemView.findViewById<TextView>(R.id.add_add_kart_button)
                addButton.text =
                    activity.application.resources.getString(R.string.add_chose_fragment_add_kart)

                //Action
                addButton.setOnClickListener { activity.onAddKartPress() }

            } else {
                holder.itemView.findViewById<TextView>(R.id.add_add_kart_button)
                    .text = activity.application.resources
                    .getString(R.string.add_chose_fragment_add_karting_center)
            }
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

    //Holder
    class PickerVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
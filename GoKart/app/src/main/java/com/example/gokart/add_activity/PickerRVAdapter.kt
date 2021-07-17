package com.example.gokart.add_activity

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.R

class PickerRVAdapter(application: Application, private var items : MutableList<String>)
    : RecyclerView.Adapter<PickerRVAdapter.PickerVHolder>() {

    //Values
    private val addPosition = 0
    private val inflater = LayoutInflater.from(application.applicationContext)

    //Create Views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickerVHolder {
        return if (viewType == addPosition){
            PickerVHolder(
                inflater.inflate(R.layout.view_add_picker, parent, false)
            )
        }else{
            PickerVHolder(
                inflater.inflate(R.layout.view_kart_picker_object, parent, false)
            )
        }
    }

    //Assign Values
    override fun onBindViewHolder(holder: PickerVHolder, position: Int) {
        if (position != addPosition)
            holder.itemView.findViewById<TextView>(R.id.picker_kart_name).text = items[position]
    }

    //Companion Functions
    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    //Holder
    class PickerVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
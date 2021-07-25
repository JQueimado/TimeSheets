package com.example.gokart.main_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.R
import com.example.gokart.database.entity.TimeSheetWithLaps

class TimeSheetsRVAdapter(context: Context, items : List<TimeSheetWithLaps> )
    : RecyclerView.Adapter<TimeSheetsRVAdapter.TimeSheetsVHolder>() {

    //Constants
    private val statsPosition :Int = 0

    //values
    private val inflater = LayoutInflater.from(context)
    private var itemList = items

    //Creates views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSheetsVHolder {
        return if (viewType == statsPosition )
            TimeSheetsVHolder( Stats.inflate(parent, inflater) )
        else
            TimeSheetsVHolder(TimeSheet.inflate(parent, inflater, itemList[viewType - 1]))
    }

    //Sets values
    override fun onBindViewHolder(holder: TimeSheetsVHolder, position: Int) {
        if ( position == statsPosition )
            Stats.setValues()
        else
            TimeSheet.setValues(itemList[position - 1], holder.itemView)
    }

    //Extras
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return itemList.size+1
    }

    //Data Set
    fun setData(timeSheets: List<TimeSheetWithLaps> ){
        itemList = timeSheets
        notifyDataSetChanged()
    }

    //ViewHolder
    class TimeSheetsVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
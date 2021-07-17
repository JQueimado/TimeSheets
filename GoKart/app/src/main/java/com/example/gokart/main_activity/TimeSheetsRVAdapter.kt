package com.example.gokart.main_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TimeSheetsRVAdapter(context: Context, items : MutableList<TimeSheet> )
    : RecyclerView.Adapter<TimeSheetsRVAdapter.TimeSheetsVHolder>() {

    private val statsPosition :Int = 0

    private val inflater = LayoutInflater.from(context)
    private var itemList = items
    private val itemCount = items.size+1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSheetsVHolder {
        return if (viewType == statsPosition )
            TimeSheetsVHolder( Stats.inflate(parent, inflater) )
        else
            TimeSheetsVHolder( TimeSheet.inflate(parent, inflater, itemList[viewType-1]) )
    }

    override fun onBindViewHolder(holder: TimeSheetsVHolder, position: Int) {
        if ( position == statsPosition )
            Stats.setValues()
        else
            TimeSheet.setValues(itemList[position - 1])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    fun setTimeSheets( timeSheets: MutableList<TimeSheet> ){
        itemList = timeSheets
        notifyDataSetChanged()
    }

    //ViewHolder
    class TimeSheetsVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
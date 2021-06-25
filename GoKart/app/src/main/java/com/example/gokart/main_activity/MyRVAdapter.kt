package com.example.gokart.main_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.database.entity.TimeSheetWithLaps

class MyRVAdapter( context: Context, items : MutableList<TimeSheet> ) : RecyclerView.Adapter<MyRVAdapter.MyVHolder>() {

    private val STATS_POSITION :Int = 0

    private val inflater = LayoutInflater.from(context)
    private val itemList = items
    private val itemCount = items.size+1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVHolder {
        return if (viewType == STATS_POSITION )
            MyVHolder( Stats.inflate(parent, inflater) )
        else
            MyVHolder( TimeSheet.inflate(parent, inflater, itemList[viewType-1]) )
    }

    override fun onBindViewHolder(holder: MyVHolder, position: Int) {
        if ( position == STATS_POSITION )
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

    //ViewHolder
    class MyVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
package com.example.gokart.main_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.R
import com.example.gokart.database.entity.StatsEntity
import com.example.gokart.database.entity.TimeSheetWithLaps

class TimeSheetsRVAdapter(
    val activity: MainActivity,
    items : List<TimeSheetWithLaps>
    ): RecyclerView.Adapter<TimeSheetsRVAdapter.TimeSheetsVHolder>() {

    //Constants
    private val statsPosition :Int = 0

    //values
    private val viewModel = activity.getViewModel()
    private val inflater = LayoutInflater.from(activity)
    private var itemList = items
    private var stats: StatsEntity? = null

    //Creates views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSheetsVHolder {
        return if (viewType == statsPosition )
            TimeSheetsVHolder( Stats.inflate(parent, inflater) )
        else
            TimeSheetsVHolder(TimeSheet.inflate(parent, inflater, itemList[viewType - 1]))
    }

    //Sets values
    override fun onBindViewHolder(holder: TimeSheetsVHolder, position: Int) {
        if ( position == statsPosition  ) {
            if (stats != null)
                Stats.setValues(
                    view = holder.itemView,
                    stats = stats!!,
                    favouriteKart = viewModel.getKart(stats!!.id),
                    favouriteKartingCenter = viewModel.getKartingCenter(stats!!.id),
                    activity = activity
                )
        }else{
            val timeSheet = itemList[position-1].timeSheet
            TimeSheet.setValues(
                timeSheetWithLaps = itemList[position - 1],
                itemView = holder.itemView,
                kart = viewModel.getKart(timeSheet.kartId),
                kartingCenter = viewModel.getKartingCenter(timeSheet.kartingCenterId),
                activity = activity
            )
        }
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

    fun setStats( stats: StatsEntity ){
        this.stats = stats
        notifyDataSetChanged()
    }

    //ViewHolder
    class TimeSheetsVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
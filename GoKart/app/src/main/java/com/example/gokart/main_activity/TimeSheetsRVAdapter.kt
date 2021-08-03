package com.example.gokart.main_activity

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.R
import com.example.gokart.data_converters.toTextTimeStamp
import com.example.gokart.database.entity.*
import java.util.*

class TimeSheetsRVAdapter(
    private val activity: AppCompatActivity,
    private val timeSheetActionFunction : TimeSheetActionFunction,
    private val viewModelAccess: ViewModelAccess
): ListAdapter<TimeSheetEntity, TimeSheetsRVAdapter.TimeSheetVHolder>(diffCalBack) {

    //Constants
    companion object {

        private val diffCalBack = object : ItemCallback<TimeSheetEntity>() {
            override fun areItemsTheSame(
                oldItem: TimeSheetEntity,
                newItem: TimeSheetEntity
            ): Boolean {
                return oldItem.timeSheetId == newItem.timeSheetId
            }

            override fun areContentsTheSame(
                oldItem: TimeSheetEntity,
                newItem: TimeSheetEntity
            ): Boolean {
                return oldItem.date == newItem.date &&
                    oldItem.kartId == newItem.kartId &&
                    oldItem.bestLap == newItem.bestLap &&
                    oldItem.averageLap == newItem.averageLap &&
                    oldItem.consistency == newItem.consistency &&
                    oldItem.kartId == newItem.kartId &&
                    oldItem.kartingCenterId == newItem.kartingCenterId
            }
        }

        /* TimeSheets */
        //Resources
        private const val timeSheetViewId = R.layout.view_time_sheet

        //Time Sheet Components
        private const val timeSheetDateId = R.id.time_sheets_date
        private const val timeSheetKartId = R.id.time_sheet_kart
        private const val timeSheetKartingCenterId = R.id.time_sheet_karting_center
        private const val timeSheetBestLapId = R.id.time_sheet_best_lap
        private const val timeSheetWorstLapId = R.id.time_sheet_worst_lap
        private const val timeSheetAverageLapId = R.id.time_sheet_average_lap
        private const val timeSheetConsistencyId = R.id.time_sheet_consistency

        //Buttons
        private const val timeSheetButtonsFrame = R.id.time_sheet_button_popup
        private const val deleteTimeSheetButtonId = R.id.time_sheet_delete_button
        private const val editTimeSheetButtonId = R.id.time_sheet_edit_button
    }

    //Creates views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSheetVHolder {
        val inflater = LayoutInflater.from(activity)
        val view: View = inflater.inflate(timeSheetViewId, parent, false )
        return TimeSheetVHolder(view)
    }

    //Sets values
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TimeSheetVHolder, position: Int) {
        val item = getItem(position)

        /* Stat values */
        //Date
        val date = Date(item.date)
        val dateText : String = DateFormat.format("dd/MM/yyyy hh:mm", date).toString()
        holder.dateView.text = dateText

        //Kart
        viewModelAccess.getKartById(item.kartId).observe( activity, {
            //DefineName
            val name = if( it.name.isBlank() )
                "${it.number}-${it.displacement}cc"
            else
                it.name
            //Set name
            holder.kartView.text = name
        } )

        //KartingCenter
        viewModelAccess.getKartingCenterById(item.kartingCenterId).observe( activity ) {
            holder.kartingCenterView.text = it.name
        }

        //Best lap
        holder.bestLapView.text = item.bestLap.toTextTimeStamp()

        //Worst lap
        holder.worstLapView.text = item.worstLap.toTextTimeStamp()

        //Average lap
        holder.averageLapView.text = item.averageLap.toTextTimeStamp()

        //Consistency
        holder.consistencyLapView.text = "${item.consistency}%"

        /* Laps Values */
        holder.buttonsFrameView.visibility = View.GONE

        //Click Action
        holder.itemView.isClickable = true
        holder.itemView.setOnClickListener {
            if (holder.buttonsFrameView.visibility == View.GONE)
                timeSheetActionFunction.onViewTimeSheet(item)
            else
                holder.buttonsFrameView.visibility = View.GONE
        }

        //Hold Action
        holder.itemView.setOnLongClickListener {
            holder.buttonsFrameView.visibility = View.VISIBLE
            return@setOnLongClickListener true
        }

        //On delete click
        holder.itemView.findViewById<Button>(deleteTimeSheetButtonId).setOnClickListener {
            timeSheetActionFunction.onDeleteAction(item)
        }

        //On Edit click
        holder.itemView.findViewById<Button>(editTimeSheetButtonId).setOnClickListener {
            timeSheetActionFunction.onEditAction(item)
        }
    }

    class TimeSheetVHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dateView: TextView = itemView.findViewById(timeSheetDateId)
        val kartingCenterView: TextView = itemView.findViewById(timeSheetKartingCenterId)
        val kartView: TextView = itemView.findViewById(timeSheetKartId)
        val bestLapView: TextView = itemView.findViewById(timeSheetBestLapId)
        val worstLapView: TextView = itemView.findViewById(timeSheetWorstLapId)
        val averageLapView: TextView = itemView.findViewById(timeSheetAverageLapId)
        val consistencyLapView: TextView = itemView.findViewById(timeSheetConsistencyId)
        val buttonsFrameView: ConstraintLayout = itemView.findViewById(timeSheetButtonsFrame)
    }

    //Edit Listener
    interface TimeSheetActionFunction{
        fun onDeleteAction( timeSheet: TimeSheetEntity )
        fun onEditAction( timeSheet: TimeSheetEntity )
        fun onViewTimeSheet( timeSheet: TimeSheetEntity )
    }

    interface ViewModelAccess{
        fun getKartById( id: Long ): LiveData<KartEntity>
        fun getKartingCenterById( id: Long ): LiveData<KartingCenterEntity>
    }
}
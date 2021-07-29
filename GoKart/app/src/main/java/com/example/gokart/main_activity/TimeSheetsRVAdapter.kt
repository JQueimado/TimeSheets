package com.example.gokart.main_activity

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.R
import com.example.gokart.data_converters.toTextTimeStamp
import com.example.gokart.database.entity.StatsEntity
import com.example.gokart.database.entity.TimeSheetEntity
import com.example.gokart.database.entity.TimeSheetWithLaps
import java.util.*
import kotlin.collections.ArrayList

class TimeSheetsRVAdapter(
    val activity: MainActivity,
    private val timeSheetActionFunction : TimeSheetActionFunction
    ): RecyclerView.Adapter<TimeSheetsRVAdapter.ItemVHolder>() {

    //Constants
    companion object {
        private const val statsPosition: Int = 0

        /* TimeSheets */
        //Resources
        private const val timeSheetViewId = R.layout.view_time_sheet
        private const val timeSheetItemViewId = R.layout.view_time_sheet_row

        //Time Sheet Components
        private const val timeSheetDateId = R.id.time_sheets_date
        private const val timeSheetKartId = R.id.time_sheet_kart
        private const val timeSheetKartingCenterId = R.id.time_sheet_karting_center
        private const val timeSheetBestLapId = R.id.time_sheet_best_lap
        private const val timeSheetWorstLapId = R.id.time_sheet_worst_lap
        private const val timeSheetAverageLapId = R.id.time_sheet_average_lap
        private const val timeSheetConsistencyId = R.id.time_sheet_consistency

        //Laps Components
        private const val lapsFrameID = R.id.times_sheet
        private const val lapNumberId = R.id.time_sheet_lap_number
        private const val lapValueId = R.id.time_sheet_lap
        private const val lapBestDeltaId = R.id.time_sheet_bestlap_delta
        private const val lapLastDeltaId = R.id.time_sheet_lastlap_delta

        //Buttons
        private const val timeSheetButtonsFrame = R.id.time_sheet_button_popup
        private const val deleteTimeSheetButtonId = R.id.time_sheet_delete_button
        private const val editTimeSheetButtonId = R.id.time_sheet_edit_button

        /* Stats View */
        //Stats Components
        private const val statsBestLapId = R.id.stats_best_lap
        private const val statsAverageLapId = R.id.stats_average_lap
        private const val statsConsistencyId = R.id.stats_consistency
        private const val statsFavouriteKartId = R.id.stats_favourite_kart
        private const val statsFavouriteKartingCenterId = R.id.stats_favourite_karting_center

    }

    //values
    private val viewModel = activity.getViewModel()
    private val inflater = LayoutInflater.from(activity)
    private var stats: StatsEntity? = null
    private var items:List<TimeSheetWithLaps> = ArrayList()

    //Creates views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVHolder {
        val view: View
        val holder: ItemVHolder
        if (viewType == statsPosition ) {
            view = inflater.inflate(R.layout.view_statistics, parent, false)
            holder = StatsVHolder(view)
        }
        else{
            //Inflate main view
            view = inflater.inflate(timeSheetViewId, parent, false )

            //inflate sheet view
            val lapsFrame = view.findViewById<TableLayout>(lapsFrameID)
            val lapsViews = ArrayList<View>()
            val laps = items[viewType-1].laps
            for ( e in laps ){
                val lapView = inflater.inflate(timeSheetItemViewId, lapsFrame, false)
                lapsFrame.addView(lapView)
                lapsViews.add(lapView)
            }
            holder = TimeSheetVHolder(view, lapsViews)
        }

        return holder
    }

    //Sets values
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemVHolder, position: Int) {
        if ( position == statsPosition  ) {
            if (stats != null) {
                val viewHolder = holder as StatsVHolder

                //best lap
                viewHolder.bestLapView.text = stats!!.bestLap.toTextTimeStamp()

                //Average lap
                val averageLap =
                    if( stats!!.averageLapNum != 0L )
                        (stats!!.averageLapSum/ stats!!.averageLapNum).toInt()
                    else
                        0
                viewHolder.averageLapView.text = averageLap.toTextTimeStamp()

                //Consistency
                val consistency =
                    if ( stats!!.consistencyNum != 0L )
                        ( stats!!.consistencySum/ stats!!.consistencyNum ).toInt()
                    else
                        0
                viewHolder.consistencyView.text = "$consistency%"

                //Kart
                activity.getViewModel().getKart(stats!!.favouriteKart).observe(activity, {

                    /////// TEMP
                    if (it == null)
                        return@observe
                    //////////

                    viewHolder.favouriteKartView.text =
                        if (it.name.isBlank() )
                            "${it.number}-${it.displacement}cc"
                        else
                            it.name
                })

                //Karting Center
                activity.getViewModel().getKartingCenter(stats!!.favouriteKartingCenter).observe(activity, {

                    ///// TEMP
                    if (it == null)
                        return@observe
                    //////////

                    viewHolder.favouriteKartingCenterView.text =
                        "${it.name}-layout:${it.layout}"
                })
            }
        }else{
            val item = items[position-1]
            val timeSheet = item.timeSheet
            val laps = item.laps
            val viewHolder = holder as TimeSheetVHolder

            /* Stat values */

            //Date
            val date = Date(timeSheet.date)
            val dateText : String = DateFormat.format("dd/MM/yyyy hh:mm", date).toString()
            viewHolder.dateView.text = dateText

            //Kart
            activity.getViewModel().getKart(timeSheet.kartId).observe( activity, {
                //DefineName
                val name = if( it.name.isBlank() )
                    "${it.number}-${it.displacement}cc"
                else
                    it.name
                //Set name
                viewHolder.kartView.text = name
            } )

            //KartingCenter
            activity.getViewModel().getKartingCenter(timeSheet.kartingCenterId).observe( activity, {
                viewHolder.kartingCenterView.text = it.name
            } )

            //Best lap
            viewHolder.bestLapView.text = timeSheet.bestLap.toTextTimeStamp()

            //Worst lap
            viewHolder.worstLapView.text = timeSheet.worstLap.toTextTimeStamp()

            //Average lap
            viewHolder.averageLapView.text = timeSheet.averageLap.toTextTimeStamp()

            //Consistency
            viewHolder.consistencyLapView.text = "${timeSheet.consistency}%"

            /* Laps Values */
            //lap values
            for (i in laps.indices) {
                val lapView = viewHolder.lapsViews[i]
                val lapValues = laps[i]
                //Lap Number
                lapView.lapNumberView.text = lapValues.number.toString()
                //Lap time
                lapView.lapValueView.text = lapValues.time
                //Lap best delta
                lapView.lapBestDeltaView.text = lapValues.bestDelta
                //Lap last delta
                lapView.lapLastDeltaView.text = lapValues.lastDelta
            }

            viewHolder.buttonsFrameView.visibility = View.GONE

            //Click Action
            viewHolder.itemView.isClickable = true
            viewHolder.itemView.setOnClickListener {
                viewHolder.buttonsFrameView.visibility = View.GONE
            }

            //Hold Action
            viewHolder.itemView.setOnLongClickListener {
                viewHolder.buttonsFrameView.visibility = View.VISIBLE
                return@setOnLongClickListener true
            }

            //On delete click
            viewHolder.itemView.findViewById<Button>(deleteTimeSheetButtonId).setOnClickListener {
                timeSheetActionFunction.onDeleteAction(timeSheet)
            }

            //On Edit click
            viewHolder.itemView.findViewById<Button>(editTimeSheetButtonId).setOnClickListener {
                timeSheetActionFunction.onEditAction(timeSheet)
            }
        }
    }

    //Extras
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size+1
    }

    //Data Set
    fun setData(timeSheets: List<TimeSheetWithLaps> ){
        items = timeSheets
        notifyDataSetChanged()
    }

    fun setStats( stats: StatsEntity ){
        this.stats = stats
        notifyDataSetChanged()
    }

    //ViewHolder
    open class ItemVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class StatsVHolder(itemView: View) : ItemVHolder(itemView) {
        val bestLapView: TextView = itemView.findViewById(statsBestLapId)
        val averageLapView: TextView = itemView.findViewById(statsAverageLapId)
        val consistencyView: TextView = itemView.findViewById(statsConsistencyId)
        val favouriteKartView: TextView = itemView.findViewById(statsFavouriteKartId)
        val favouriteKartingCenterView: TextView =
            itemView.findViewById(statsFavouriteKartingCenterId)
    }

    class TimeSheetVHolder(itemView: View, laps:List<View>): ItemVHolder(itemView){
        val dateView: TextView = itemView.findViewById(timeSheetDateId)
        val kartingCenterView: TextView = itemView.findViewById(timeSheetKartingCenterId)
        val kartView: TextView = itemView.findViewById(timeSheetKartId)
        val bestLapView: TextView = itemView.findViewById(timeSheetBestLapId)
        val worstLapView: TextView = itemView.findViewById(timeSheetWorstLapId)
        val averageLapView: TextView = itemView.findViewById(timeSheetAverageLapId)
        val consistencyLapView: TextView = itemView.findViewById(timeSheetConsistencyId)

        val buttonsFrameView: ConstraintLayout = itemView.findViewById(timeSheetButtonsFrame)

        val lapsViews: MutableList<LapsViews>

        init {
            lapsViews = ArrayList()
            for (lap in laps)
                lapsViews.add( LapsViews(lap) )
        }

        class LapsViews( lapView:View ){
            val lapNumberView: TextView = lapView.findViewById(lapNumberId)
            val lapValueView: TextView = lapView.findViewById(lapValueId)
            val lapBestDeltaView: TextView = lapView.findViewById(lapBestDeltaId)
            val lapLastDeltaView: TextView = lapView.findViewById(lapLastDeltaId)
        }
    }

    //Edit Listener
    interface TimeSheetActionFunction{
        fun onDeleteAction( timeSheet: TimeSheetEntity )
        fun onEditAction( timeSheet: TimeSheetEntity )
    }
}
package com.example.gokart.time_sheet_activity

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gokart.R
import com.example.gokart.data_converters.toTextTimeStamp
import com.example.gokart.database.entity.LapEntity
import com.example.gokart.database.entity.TimeSheetEntity
import com.example.gokart.database.entity.TimeSheetWithLaps
import java.util.*

class TimeSheetView(
    activity: AppCompatActivity,
    val databaseInterface: DatabaseAccessInterface
    ) : LinearLayout(activity) {

    companion object{
        //Views
        private const val timeSheetWithLapsId = R.layout.view_time_sheet_with_laps
        private const val lapViewId = R.layout.view_time_sheet_row

        // Time Sheet Contents
        private const val dateId = R.id.time_sheets_date
        private const val kartingCenterId = R.id.time_sheet_karting_center
        private const val kartId = R.id.time_sheet_kart
        private const val bestLapId = R.id.time_sheet_best_lap
        private const val worstLapId = R.id.time_sheet_worst_lap
        private const val averageLapId = R.id.time_sheet_average_lap
        private const val consistencyId = R.id.time_sheet_consistency
        private const val lapsPlaceholderId = R.id.time_sheet_lap_placeholder

        // Lap Contents
        private const val lapNumberId = R.id.lap_number
        private const val lapValueId = R.id.lap_value
        private const val bestDeltaId = R.id.lap_best_delta
        private const val lastDeltaId = R.id.lap_last_delta
    }

    val inflater: LayoutInflater

    private val dateView: TextView
    private val kartingCenterView: TextView
    private val kartView: TextView
    private val bestLapView: TextView
    private val worstLapView: TextView
    private val averageLapView: TextView
    private val consistencyView: TextView

    private val lapsPlaceholderView: TableLayout

    init {
        //Fix view
        inflater = LayoutInflater.from(activity)
        inflater.inflate(timeSheetWithLapsId, this, true)

        //Extract features
        dateView = this.findViewById(dateId)
        kartingCenterView = this.findViewById(kartingCenterId)
        kartView = this.findViewById(kartId)
        bestLapView = this.findViewById(bestLapId)
        worstLapView = this.findViewById(worstLapId)
        averageLapView = this.findViewById(averageLapId)
        consistencyView = this.findViewById(consistencyId)
        lapsPlaceholderView = this.findViewById(lapsPlaceholderId)
    }

    fun setValues( timeSheetWithLaps: TimeSheetWithLaps ){

        val timeSheet = timeSheetWithLaps.timeSheet
        val laps = timeSheetWithLaps.laps

        //sets views values for the header components
        setHeaderValues(timeSheet)

        //sets the views for the lap values
        setLapValues( laps )

    }

    @SuppressLint("SetTextI18n")
    private fun setHeaderValues(timeSheet: TimeSheetEntity ){

        //date
        val date = Date(timeSheet.date)
        val dateText : String = DateFormat.format("dd/MM/yyyy hh:mm", date).toString()
        dateView.text = dateText

        //KartingCenter
        databaseInterface.setKartingCenter(kartingCenterView, timeSheet.kartingCenterId)

        //Kart
        databaseInterface.setKart(kartView, timeSheet.kartId)

        //Best lap
        bestLapView.text = timeSheet.bestLap.toTextTimeStamp()

        //Worst lap
        worstLapView.text = timeSheet.worstLap.toTextTimeStamp()

        //Average lap
        averageLapView.text = timeSheet.averageLap.toTextTimeStamp()

        //Consistency
        consistencyView.text = "${timeSheet.consistency}%"

    }

    private fun setLapValues( lapEntities: List<LapEntity> ){
        for ( lapEntity in lapEntities){
            val lapView = inflater.inflate(lapViewId, lapsPlaceholderView, false)
            lapsPlaceholderView.addView(lapView)

            //Lap number
            lapView.findViewById<TextView>(lapNumberId).text = lapEntity.number.toString()

            //lap value
            lapView.findViewById<TextView>(lapValueId).text = lapEntity.time

            //lap best delta
            lapView.findViewById<TextView>(bestDeltaId).text = lapEntity.bestDelta

            //lap last delta
            lapView.findViewById<TextView>(lastDeltaId).text = lapEntity.lastDelta

        }
    }

    interface DatabaseAccessInterface{
        //Sets the text value to the karting center name
        fun setKartingCenter( kartingCenterView: TextView, kartingCenterId: Long )

        //Sets the text value to the kart name
        fun setKart( kartView: TextView, kartId: Long )
    }

}
package com.example.gokart.main_activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gokart.R
import com.example.gokart.data_converters.toTextTimeStamp
import com.example.gokart.database.entity.StatsEntity

@SuppressLint("ViewConstructor")
class StatsView(activity: AppCompatActivity) : TableLayout(activity) {

    companion object{
        /* Stats View */
        private const val statsViewId = R.layout.view_statistics

        //Stats Components IDs
        private const val statsBestLapId = R.id.stats_best_lap
        private const val statsAverageLapId = R.id.stats_average_lap
        private const val statsConsistencyId = R.id.stats_consistency
        private const val statsFavouriteKartId = R.id.stats_favourite_kart
        private const val statsFavouriteKartingCenterId = R.id.stats_favourite_karting_center
    }

    //Stats Components
    private val bestLapView: TextView
    private val averageLapView: TextView
    private val consistencyView: TextView
    private val kartView: TextView
    private val kartingCenterView: TextView

    init {
        val inflater = LayoutInflater.from(activity)
        inflater.inflate( statsViewId, this, true)

        bestLapView = this.findViewById(statsBestLapId)
        averageLapView = this.findViewById(statsAverageLapId)
        consistencyView = this.findViewById(statsConsistencyId)
        kartView = this.findViewById(statsFavouriteKartId)
        kartingCenterView = this.findViewById(statsFavouriteKartingCenterId)
    }

    fun setBestLap( bestLap: Int ){
        bestLapView.text = bestLap.toTextTimeStamp()
    }

    fun setAverageLap( averageLap: Int ){
        averageLapView.text = averageLap.toTextTimeStamp()
    }

    @SuppressLint("SetTextI18n")
    fun setConsistency(consistency: Int ){
        consistencyView.text = "${consistency}%"
    }

    fun setKart( kartName: String ){
        kartView.text = kartName
    }

    fun setKartingCenter( kartingCenterName: String ){
        kartingCenterView.text = kartingCenterName
    }
}
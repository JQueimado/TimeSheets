package com.example.gokart.main_activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.gokart.R
import com.example.gokart.data_converters.toTextTimeStamp
import com.example.gokart.database.entity.KartEntity
import com.example.gokart.database.entity.KartingCenterEntity
import com.example.gokart.database.entity.StatsEntity

class Stats() {

    companion object {

        //Stats Components
        private const val statsBestLapId = R.id.stats_best_lap
        private const val statsAverageLapId = R.id.stats_average_lap
        private const val statsConsistencyId = R.id.stats_consistency
        private const val statsFavouriteKartId = R.id.stats_favourite_kart
        private const val statsFavouriteKartingCenterId = R.id.stats_favourite_karting_center

        fun inflate( parent: ViewGroup, inflater: LayoutInflater) : View {
            return inflater.inflate(R.layout.view_statistics, parent, false)
        }

        @SuppressLint("SetTextI18n")
        fun setValues(
            view:View,
            stats: StatsEntity,
            favouriteKart: LiveData<KartEntity>,
            favouriteKartingCenter: LiveData<KartingCenterEntity>,
            activity: AppCompatActivity
        ) {
            //best lap
            view.findViewById<TextView>(statsBestLapId).text = stats.bestLap.toTextTimeStamp()

            //Average lap
            val averageLap =
                if( stats.averageLapNum != 0L )
                    (stats.averageLapSum/stats.averageLapNum).toInt()
                else
                    0
            view.findViewById<TextView>(statsAverageLapId).text = averageLap.toTextTimeStamp()

            //Consistency
            val consistency =
                if ( stats.consistencyNum != 0L )
                    ( stats.consistencySum/stats.consistencyNum ).toInt()
                else
                    0
            view.findViewById<TextView>(statsConsistencyId).text = "$consistency%"

            //Kart
            favouriteKart.observe(activity, {

                /////// TEMP
                if (it == null)
                    return@observe
                //////////

                view.findViewById<TextView>(statsFavouriteKartId).text =
                    if (it.name.isBlank() )
                        "${it.number}-${it.displacement}cc"
                    else
                        it.name
            })

            //Karting Center
            favouriteKartingCenter.observe(activity, {

                ///// TEMP
                if (it == null)
                    return@observe
                //////////

                view.findViewById<TextView>(statsFavouriteKartingCenterId).text =
                    "${it.name}-layout:${it.layout}"
            })
        }

    }

}
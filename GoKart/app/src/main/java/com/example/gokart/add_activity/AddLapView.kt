package com.example.gokart.add_activity

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gokart.R
import com.example.gokart.data_converters.toTextTimeStamp
import com.google.android.material.textfield.TextInputEditText

//Lap View
@SuppressLint("ViewConstructor", "SetTextI18n")
class AddLapView(
    activity: AddActivity,
    val position: Int,
    var value: String,
    var parent: ViewGroup
) {

    //constants
    companion object{
        //View
        private const val itemViewId = R.layout.view_add_lap_row
        //Components
        private const val lapNumberId = R.id.lap_number
        private const val lapInputEditTextId = R.id.lap_input
        private const val lapRemoveButtonId = R.id.lap_remove_button
    }

    val view: View
    private val lapView: TextView
    private val lapInputEditText: TextInputEditText

    init {
        val inflater = activity.layoutInflater
        view = inflater.inflate(itemViewId, parent, true)

        lapView = view.findViewById(lapNumberId)
        lapView.text = "${position+1}-${value.toTextTimeStamp()}"

        lapInputEditText = view.findViewById(lapInputEditTextId)
        lapInputEditText.setText(value, TextView.BufferType.SPANNABLE)
        lapInputEditText.addTextChangedListener( object : TextWatcher {

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                //Nothing needed
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val textRaw = p0.toString()
                val text = textRaw.toTextTimeStamp()
                value = textRaw
                lapView.text = "${position+1}-$text"
                activity.setAddLapValue(position, textRaw)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //Remove
        val removeButton = view.findViewById<Button>(lapRemoveButtonId)
        removeButton.setOnClickListener{
            activity.removeAddLapView(this)
        }
        //// Debug //////
        Log.d("AddLapView:", "created AddLapView with text $value")
    }
}
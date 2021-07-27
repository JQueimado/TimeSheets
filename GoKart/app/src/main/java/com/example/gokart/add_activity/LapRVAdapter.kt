package com.example.gokart.add_activity

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gokart.R
import com.example.gokart.data_converters.toTextTimeStamp
import com.google.android.material.textfield.TextInputEditText

class LapRVAdapter( var items: MutableList<String>, val activity: AddActivity ): RecyclerView.Adapter<LapRVAdapter.LapVHolder>() {

    //constants
    companion object{
        //View
        private const val itemViewId = R.layout.view_add_lap_row
        //Components
        private const val lapNumberId = R.id.lap_number
        private const val lapInputEditTextId = R.id.lap_input
        private const val lapRemoveButtonId = R.id.lap_remove_button
    }

    //values
    private val inflater = activity.layoutInflater

    //View Holder
    class LapVHolder( itemView: View): RecyclerView.ViewHolder(itemView){
        val lapView: TextView = itemView.findViewById(lapNumberId)
        val lapInputEditText: TextInputEditText = itemView.findViewById(lapInputEditTextId)
        val lapRemoveButton: Button = itemView.findViewById(lapRemoveButtonId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LapVHolder {
        val view = inflater.inflate(itemViewId, parent, false)
        return LapVHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LapVHolder, position: Int) {
        val value = items[position]

        holder.lapView.text = "${holder.position+1}-${value.toTextTimeStamp()}"

        holder.lapInputEditText.setText(value, TextView.BufferType.EDITABLE)
        holder.lapInputEditText.addTextChangedListener( object : TextWatcher {

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
                holder.lapView.text = "${position+1}-$text"
                items[position] = textRaw
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing needed
            }
        })

        //Remove
        holder.lapRemoveButton.setOnClickListener{
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData( items: MutableList<String> ){
        this.items = items
        notifyDataSetChanged()
    }

}
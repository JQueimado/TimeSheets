package com.example.gokart.add_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.example.gokart.R
import com.example.gokart.database.entity.KartingCenterEntity
import com.google.android.material.textfield.TextInputEditText

class AddKartingCenterFragment( val addActivity: AddActivity ) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_add_karting_center,
            container,
            false
        )

        //Inputs
        val nameInput = view.findViewById<TextInputEditText>(R.id.add_karting_center_name_input)
        val layoutInput = view.findViewById<TextInputEditText>(R.id.add_karting_center_layout_input)

        //Confirm
        view.findViewById<Button>(R.id.add_karting_center_confirm_button).setOnClickListener{
            var name = nameInput.text.toString()
            val layoutRaw = layoutInput.text.toString()

            try {
                //Check if there is input
                if( name.isBlank() || name.isEmpty() )
                    throw Exception("Name is Empty")

                //Check if there is layout input
                if( layoutRaw.isBlank() || layoutRaw.isEmpty() )
                    throw  Exception("Layout is required")
                if( !layoutRaw.isDigitsOnly() )
                    throw Exception("Layout must be a number")

                val layout = layoutRaw.toInt()
                name = "$name-layout:$layout"
                val kartingCenterEntity = KartingCenterEntity(name,0, layout)

                addActivity.onAddKartingCenterConclude( kartingCenterEntity )

            }catch (e: Exception){
                Toast.makeText(addActivity.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }

        //Back Button
        view.findViewById<Button>(R.id.add_karting_center_back_button).setOnClickListener{
            addActivity.onCloseAddKartingCenter()
        }

        return view
    }
}
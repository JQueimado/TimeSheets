package com.example.gokart.add_activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.example.gokart.R
import com.example.gokart.database.entity.KartingCenterEntity
import com.google.android.material.textfield.TextInputEditText

class AddKartingCenterFragment( val addActivity: AddActivity ) : Fragment() {

    private lateinit var nameInput: TextInputEditText
    private lateinit var layoutInput: TextInputEditText

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
        nameInput = view.findViewById(R.id.add_karting_center_name_input)
        layoutInput = view.findViewById(R.id.add_karting_center_layout_input)

        //nameInput handler
        nameInput.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    layoutInput.requestFocus()
                    true
                }
                else -> false
            }
        }

        //layoutInput handler
        layoutInput.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    confirm()
                    layoutInput.clearFocus()
                    true
                }
                else -> false
            }
        }

        //Confirm
        view.findViewById<Button>(R.id.add_karting_center_confirm_button).setOnClickListener{
            confirm()
        }

        //Back Button
        view.findViewById<Button>(R.id.add_karting_center_back_button).setOnClickListener{
            addActivity.onCloseAddKartingCenter()
        }

        return view
    }

    private fun confirm(){
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
}
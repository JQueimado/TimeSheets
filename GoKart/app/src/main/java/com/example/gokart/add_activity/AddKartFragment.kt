package com.example.gokart.add_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.gokart.R
import com.example.gokart.database.entity.KartEntity
import com.google.android.material.textfield.TextInputEditText
import java.lang.Exception

class AddKartFragment(val parent: AddActivity) : Fragment() {

    //Input Values
    private var name = ""
    private var number = ""
    private var displacement = ""

    //Ui
    private lateinit var nameTextInput : TextInputEditText
    private lateinit var numberTextInput : TextInputEditText
    private lateinit var displacementTextInput : TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_kart, container, false)

        //Set Ui values
        nameTextInput = view.findViewById(R.id.add_karting_center_location_button)
        numberTextInput = view.findViewById(R.id.add_karting_center_name_input)
        displacementTextInput = view.findViewById(R.id.add_karting_center_layout_input)

        //action handlers
        nameTextInput.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    nameTextInput.clearFocus()
                    confirm()
                    true
                }
                else -> false
            }
        }

        numberTextInput.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    displacementTextInput.requestFocus()
                    true
                }
                else -> false
            }
        }

        displacementTextInput.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    nameTextInput.requestFocus()
                    true
                }
                else -> false
            }
        }

        //OK
        view.findViewById<Button>(R.id.add_karting_center_confirm_button)
            .setOnClickListener {confirm()}

        //Back
        view.findViewById<Button>(R.id.add_karting_center_back_button).setOnClickListener {
            parent.onCloseAddKart()
        }

        return view
    }

    private fun confirm(){
        //Extract raw input
        name = nameTextInput.text.toString()
        number = numberTextInput.text.toString()
        displacement = displacementTextInput.text.toString()

        try {

            //Check values
            if (
                number.isBlank() ||
                number.isEmpty() ||
                !number.isDigitsOnly()
            ) throw Exception("Invalid Number")

            if (
                displacement.isBlank() ||
                displacement.isEmpty() ||
                !displacement.isDigitsOnly()
            ) throw Exception("Invalid Displacement")

            val kartEntity = KartEntity(
                kartingCenter = parent.getKartingCenterID(),
                number= number.toInt(),
                displacement = displacement.toInt(),
                name =  if( name.isBlank() )
                    ""
                else
                    name
            )
            parent.onAddKartConclude(kartEntity)
        }catch ( e: Exception ){
            Toast.makeText(parent, e.message, Toast.LENGTH_LONG).show()
        }
    }

}
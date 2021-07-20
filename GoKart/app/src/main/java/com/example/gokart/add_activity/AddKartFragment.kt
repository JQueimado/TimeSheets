package com.example.gokart.add_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import com.example.gokart.R
import com.google.android.material.textfield.TextInputEditText

class AddKartFragment(val parent: AddActivity) : Fragment() {

    //Kart view model
    private val kartsViewModel : KartsViewModel by viewModels()

    //Input Values
    private var name = ""
    private var number = ""
    private var displacement = ""

    //Ui
    private lateinit var nameTextInput : TextInputEditText
    private lateinit var numberTextInput : TextInputEditText
    private lateinit var displacementTextInput : TextInputEditText

    //On click actions
    private val onConfirmClick = View.OnClickListener{
        //Extract raw input
        name = nameTextInput.text.toString()
        number = numberTextInput.text.toString()
        displacement = numberTextInput.toString()

        //Check values
        if (
            number.isBlank() ||
            number.isEmpty() ||
            !number.isDigitsOnly()
        ) return@OnClickListener

        if (
            displacement.isBlank() ||
            displacement.isEmpty() ||
            !displacement.isDigitsOnly()
        ) return@OnClickListener

        //TODO( Kart entity and insert into DB )

        parent.onAddKartBackPress()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_kart, container, false)

        //Set Ui values
        nameTextInput = view.findViewById(R.id.add_kart_name)
        numberTextInput = view.findViewById(R.id.add_kart_number)
        displacementTextInput = view.findViewById(R.id.add_kart_displacement)

        //OK
        view.findViewById<Button>(R.id.add_kart_confirm_button).setOnClickListener( onConfirmClick )

        //Back
        view.findViewById<Button>(R.id.add_kart_back_button).setOnClickListener {
            parent.onAddKartBackPress()
        }

        return view
    }

}
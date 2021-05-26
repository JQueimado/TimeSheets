package com.example.gokart

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.DecorContentParent
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(parent: AddActivity) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val parent : AddActivity = parent

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(parent, this, year,month,day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        parent.onDatePick(year, month, day)
        // Do something with the date chosen by the user
    }
}

package com.mahozi.sayed.comparisist.products.create

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DatePickerDialogFragment(private val onDateReceived: OnDateReceived): DialogFragment(), DatePickerDialog.OnDateSetListener {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        val stringDate = year.toString() + "/" + (month + 1) + "/" + day
        val date: Date
        try {

            val dateFormat = SimpleDateFormat("yyyy/MM/dd")
            date = dateFormat.parse(stringDate)

            onDateReceived.getDate(dateFormat.format(date))
        } catch (e: ParseException) {
        }
    }



    interface OnDateReceived {
        fun getDate(date: String?)
    }
}
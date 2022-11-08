package com.tcc.alif.view.base

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

class DatePickerHelper (
    private val context: Context,
    private val currentDate: String? = null,
    val selectedDate: (calendar: Calendar) -> Unit
) {
    private lateinit var datePickerDialog: DatePickerDialog

    fun create(): DatePickerDialog{
        val calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            context,
            {_,year, month, dayOfMonth ->
                val dateSelected = Calendar.getInstance()
                dateSelected.set(year, month, dayOfMonth)
                selectedDate.invoke(dateSelected)
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )

        return datePickerDialog
    }

}
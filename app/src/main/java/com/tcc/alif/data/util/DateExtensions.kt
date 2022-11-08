package com.tcc.alif.data.util

import android.content.Context
import android.widget.EditText
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT
import com.tcc.alif.view.base.DatePickerHelper
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun Date.toStringDate(format: String): String{
    return try {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
        val newDate = dateFormat.format(this)
        newDate.toString()
    }catch (e: Exception){
        "ERROR DATE PARSER"
    }
}

fun EditText.createDatePicker(context: Context){
    DatePickerHelper(
        context = context,
        selectedDate = { calendar ->
            this.setText(calendar.time.toStringDate(NORMAL_DATE_WITH_HOURS_FORMAT))
        }
    ).create()
        .show()
}

object DateFormats{

    const val NORMAL_DATE_FORMAT = "dd/MM/yyyy"
    const val NORMAL_DATE_WITH_HOURS_FORMAT = "dd/MM/yyyy HH:mm:ss"

}
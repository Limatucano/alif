package com.tcc.alif.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT
import com.tcc.alif.view.base.DatePickerHelper
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(
    dateFormat: String,
    locale: Locale = Locale(Constants.LANGUAGE_PT, Constants.COUNTRY_BR),
): Date?{
    val date = SimpleDateFormat(
        dateFormat,
        locale
    )
    return date.format(Date()).dateFromString(dateFormat)
}

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

@SuppressLint("SimpleDateFormat")
fun String.changeDateFormat(
    currentFormat: String,
    newFormat: String,
    isUTC: Boolean = false
): String{
    return try{
        val oldDateFormat = SimpleDateFormat(currentFormat)
        val newDateFormat = SimpleDateFormat(newFormat)
        val date = oldDateFormat.parse(this)

        if(isUTC) newDateFormat.timeZone = TimeZone.getTimeZone("gmt")
        newDateFormat.format(date)
    } catch (e: Exception){
        e.printStackTrace()
        this
    }
}
fun String.dateFromString(
    dateFormat: String,
    locale: Locale = Locale(Constants.LANGUAGE_PT, Constants.COUNTRY_BR),
    isUTC: Boolean = false
): Date?{
    return try{
        val date = if(isUTC) changeDateFormat(dateFormat, dateFormat, true) else this
        val dateFormat = SimpleDateFormat(dateFormat, locale)
        dateFormat.parse(date)
    }catch (e: Exception){
        e.printStackTrace()
        null
    }
}

object DateFormats{
    const val NORMAL_DATE_FORMAT = "dd/MM/yyyy"
    const val NORMAL_DATE_WITH_HOURS_FORMAT = "dd/MM/yyyy HH:mm:ss"

}
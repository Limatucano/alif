package com.tcc.alif.data.util

import com.google.firebase.Timestamp
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
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

object DateFormats{

    const val NORMAL_DATE_FORMAT = "dd/MM/yyyy"
    const val NORMAL_DATE_WITH_HOURS_FORMAT = "dd/MM/yyyy HH:mm:ss"

}
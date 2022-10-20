package com.tcc.alif.data.util

import com.google.firebase.Timestamp
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun Date.toStringDate(format: String): String{
    return try {
        val dateFormat = SimpleDateFormat(format)
        val newDate = dateFormat.format(this)
        newDate.toString()
    }catch (e: Exception){
        "ERROR DATE PARSER"
    }
}

object DateFormats{

    const val NORMAL_DATE_FORMAT = "dd/MM/yyyy"

}
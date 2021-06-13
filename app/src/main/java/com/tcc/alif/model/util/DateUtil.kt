package com.tcc.alif.model.util

import android.os.Build
import java.util.*


class DateUtil {
    companion object{
        fun myValidateDate(date : String) : Boolean {
            val dateClean    = date.replace("/","")
            if(dateClean.length != 8)
                return false

            var year_born = dateClean.substring(4,8).toLong()
            val year      = Calendar.getInstance().get(Calendar.YEAR).toLong()
            var total     = year - year_born

            return total in 0..120
        }
    }
}
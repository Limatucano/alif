package com.tcc.alif.model.util

import android.os.Build
import android.util.Log
import java.util.*


class DateUtil {
    companion object{
        fun myValidateDate(date : String) : Boolean {
            val dateClean    = date.replace("/","")
            if(dateClean.length != 8)
                return false

            var year_born = dateClean.substring(4,8).toLong()
            var day = dateClean.substring(0,2).toLong()
            var month = dateClean.substring(2,4).toLong()

            val year      = Calendar.getInstance().get(Calendar.YEAR).toLong()
            var total     = year - year_born
            if(day>31) return false
            if(month>12) return false
            return total in 0..120
        }
    }
}
package com.tcc.alif.data.util

fun String?.emptyIfNull() : String = if(this == "null" || this == null) "" else this
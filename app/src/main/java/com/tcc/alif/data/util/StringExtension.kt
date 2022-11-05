package com.tcc.alif.data.util

import android.text.Editable
import android.text.Html

fun String?.emptyIfNull() : String = if(this == "null" || this == null) "" else this

fun String.fromHtml() = Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)

fun Editable?.size(): Int{
    if(this == null){
        return 0
    }
    return this.length
}
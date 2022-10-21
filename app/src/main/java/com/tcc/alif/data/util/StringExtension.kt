package com.tcc.alif.data.util

import android.text.Html

fun String?.emptyIfNull() : String = if(this == "null" || this == null) "" else this

fun String.fromHtml()= Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
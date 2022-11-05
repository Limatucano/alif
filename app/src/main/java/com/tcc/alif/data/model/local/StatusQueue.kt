package com.tcc.alif.data.model.local

import androidx.annotation.StringRes
import com.tcc.alif.R

enum class StatusQueue(@StringRes val text: Int) {
    OPEN(R.string.opened_status),
    CLOSED(R.string.closed_status)
}
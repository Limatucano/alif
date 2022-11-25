package com.tcc.alif.data.model.local

import androidx.annotation.StringRes
import com.tcc.alif.R
import com.tcc.alif.data.model.QueueResponse.Companion.CLOSED_STATUS
import com.tcc.alif.data.model.QueueResponse.Companion.OPENED_STATUS

enum class StatusQueue(
    @StringRes val text: Int,
    val request: String,
    @StringRes val buttonText: Int
    ) {
    OPEN(
        text = R.string.opened_status,
        request = OPENED_STATUS,
        buttonText = R.string.opened_status_button
    ),
    CLOSED(
        text = R.string.closed_status,
        request = CLOSED_STATUS,
        buttonText = R.string.closed_status_button
    );

    companion object{
        fun getByStringRes(text: Int?) =
            values().firstOrNull { it.text == text } ?: CLOSED
    }
}
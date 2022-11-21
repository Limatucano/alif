package com.tcc.alif.data.model.local

import androidx.annotation.StringRes
import com.tcc.alif.R

enum class EmployeeStatus(
    val value: String,
    @StringRes val text: Int
) {
    ACCEPTED_STATUS(
        value = "ACEITO",
        text = R.string.employee_accepted_status
    ),
    WAITING_STATUS(
        value = "AGUARDANDO",
        text = R.string.employee_waiting_status
    ),
    REFUSED_STATUS(
        value = "RECUSADO",
        text = R.string.employee_refused_status
    )
}
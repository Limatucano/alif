package com.tcc.alif.view.ui.administrator.queue

import com.tcc.alif.data.model.CallStatus

sealed class QueueIntent{
    data class GetCalls(val idQueue : String) : QueueIntent()
    data class UpdateCallStatus(
        val status: CallStatus,
        val idUser: String,
        val idEmployee: String,
        val idQueue: String
    ) : QueueIntent()
}

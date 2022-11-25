package com.tcc.alif.view.ui.administrator.queue

import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.model.local.StatusQueue

sealed class QueueIntent{
    data class GetCalls(val idQueue : String) : QueueIntent()
    data class UpdateCallStatus(
        val status: CallStatus,
        val idUser: String,
        val idEmployee: String,
        val idQueue: String
    ) : QueueIntent()
    data class UpdateQueueStatus(
        val status: StatusQueue,
        val idQueue: String
    ) : QueueIntent()
}

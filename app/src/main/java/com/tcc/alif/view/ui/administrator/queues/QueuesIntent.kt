package com.tcc.alif.view.ui.administrator.queues

import com.tcc.alif.data.model.QueueRequest
import com.tcc.alif.data.model.QueueResponse

sealed class QueuesIntent{

    data class GetQueuesBy(
        val filter: String = "",
        val idCompany: String
    ): QueuesIntent()

    data class SaveNewQueue(
        val queue: QueueRequest
    ) : QueuesIntent()
}

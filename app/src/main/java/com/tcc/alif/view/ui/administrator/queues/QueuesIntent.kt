package com.tcc.alif.view.ui.administrator.queues

import com.tcc.alif.data.model.QueueRequest

sealed class QueuesIntent{

    data class GetQueuesBy(
        val filter: String = "",
        val idCompany: String
    ): QueuesIntent()

    data class SaveNewQueue(
        val queue: QueueRequest
    ) : QueuesIntent()

    data class UpdateQueue(
        val queue: QueueRequest
    ) : QueuesIntent()

    data class GetAllCategories(
        val idCompany: String
    ) : QueuesIntent()

    data class GetMyEmployees(
        val idCompany: String
    ) : QueuesIntent()
}

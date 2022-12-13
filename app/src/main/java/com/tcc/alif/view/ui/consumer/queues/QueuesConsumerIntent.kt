package com.tcc.alif.view.ui.consumer.queues

sealed class QueuesConsumerIntent{
    data class SearchQueues(
        val filter: String,
        val byQrCode: Boolean = false
    ) : QueuesConsumerIntent()
}

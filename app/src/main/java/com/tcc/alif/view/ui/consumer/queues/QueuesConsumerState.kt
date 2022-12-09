package com.tcc.alif.view.ui.consumer.queues

import com.tcc.alif.data.model.QueueResponse

sealed class QueuesConsumerState{
    data class Loading(val loading: Boolean) : QueuesConsumerState()
    data class Error(val message: String) : QueuesConsumerState()
    data class Queues(val queues: List<QueueResponse>) : QueuesConsumerState()
}

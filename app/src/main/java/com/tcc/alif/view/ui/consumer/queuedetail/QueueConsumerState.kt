package com.tcc.alif.view.ui.consumer.queuedetail

import com.tcc.alif.data.model.MyQueuesResponse

sealed class QueueConsumerState {
    data class SubscribedQueue(
        val isSubscribed: Boolean,
        val idService: String? = null,
        val response: MyQueuesResponse?
    ) : QueueConsumerState()
    data class CanceledSubscription(val message: String) : QueueConsumerState()
    data class Error(val message: String) : QueueConsumerState()
    data class Loading(val loading: Boolean) : QueueConsumerState()
}
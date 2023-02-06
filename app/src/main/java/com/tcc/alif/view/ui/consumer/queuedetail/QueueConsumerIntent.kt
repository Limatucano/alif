package com.tcc.alif.view.ui.consumer.queuedetail

import com.tcc.alif.data.model.Service

sealed class QueueConsumerIntent{
    data class CheckIfUserIsSubscribed(
        val idUser: String,
        val idQueue: String
    ) : QueueConsumerIntent()
    data class CancelSubscription(
        val idQueue: String,
        val service: Service
    ) : QueueConsumerIntent()
}
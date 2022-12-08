package com.tcc.alif.view.ui.consumer.myqueues

sealed class MyQueuesConsumerIntent{
    data class GetMyQueues(val idUser: String) : MyQueuesConsumerIntent()
}

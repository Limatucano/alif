package com.tcc.alif.view.ui.consumer.myqueues

import com.tcc.alif.data.model.MyQueuesResponse

sealed class MyQueuesConsumerState{
    data class Error(val message: String) : MyQueuesConsumerState()
    data class Loading(val loading: Boolean) : MyQueuesConsumerState()
    data class MyQueues(val queues: List<MyQueuesResponse>) : MyQueuesConsumerState()
}

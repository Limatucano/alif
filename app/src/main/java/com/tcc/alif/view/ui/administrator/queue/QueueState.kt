package com.tcc.alif.view.ui.administrator.queue

import com.tcc.alif.data.model.Call

sealed class QueueState{
    data class CallUpdated(val message: String): QueueState()
    data class Calls(
        val calls: List<Call>,
        val quantity: Int
    ): QueueState()
    data class Loading(val loading: Boolean) : QueueState()
    data class Error(val message: String) : QueueState()
}
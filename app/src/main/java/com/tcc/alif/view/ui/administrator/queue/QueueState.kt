package com.tcc.alif.view.ui.administrator.queue

import com.tcc.alif.view.ui.BaseState

sealed class QueueState{
    data class CallUpdated(val message: String): QueueState()
    data class Success<T>(val response : T) : QueueState()
    data class Loading(val loading: Boolean) : QueueState()
    data class Error(val message: String) : QueueState()
}
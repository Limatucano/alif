package com.tcc.alif.view.ui.administrator.queues

import com.tcc.alif.data.model.Queues


sealed class QueuesState{

    data class Error(val message: String) : QueuesState()
    data class Loading(val loading: Boolean) : QueuesState()
    data class QueuesData(val queues: Queues) : QueuesState()
    data class QueueSaved(val message: String) : QueuesState()
}

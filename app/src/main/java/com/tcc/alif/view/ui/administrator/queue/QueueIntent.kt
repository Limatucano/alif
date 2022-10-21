package com.tcc.alif.view.ui.administrator.queue

sealed class QueueIntent{
    data class GetCalls(val idQueue : String) : QueueIntent()
}

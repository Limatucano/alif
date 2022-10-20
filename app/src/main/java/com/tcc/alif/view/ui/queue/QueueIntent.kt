package com.tcc.alif.view.ui.queue

sealed class QueueIntent{
    data class GetCalls(val idQueue : String) : QueueIntent()
}

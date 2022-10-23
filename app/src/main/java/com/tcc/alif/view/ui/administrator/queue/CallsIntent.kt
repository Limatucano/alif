package com.tcc.alif.view.ui.administrator.queue

import com.tcc.alif.data.model.Call

sealed class CallsIntent{
    data class SetInProgress(val call: Call) : CallsIntent()
    data class SetToFinish(val call: Call) : CallsIntent()
}

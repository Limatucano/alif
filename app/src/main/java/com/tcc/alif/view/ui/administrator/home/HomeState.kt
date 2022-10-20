package com.tcc.alif.view.ui.administrator.home

import com.tcc.alif.data.model.Queues

sealed class HomeState {
    data class QueuesData(val queues : Queues) : HomeState()
    data class Loading(val loading : Boolean) : HomeState()
    data class Error(val message : String) : HomeState()
}
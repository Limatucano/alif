package com.tcc.alif.view.ui.consumer.home

import com.tcc.alif.data.model.HistoricService

sealed class HomeConsumerState{
    data class Loading(val loading: Boolean) : HomeConsumerState()
    data class Error(val message: String) : HomeConsumerState()
    data class Historic(val historic: List<HistoricService>) : HomeConsumerState()
}

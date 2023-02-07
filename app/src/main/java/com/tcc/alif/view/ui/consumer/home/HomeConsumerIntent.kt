package com.tcc.alif.view.ui.consumer.home

sealed class HomeConsumerIntent{

    data class LoadHistoric(val idUser: String) : HomeConsumerIntent()
    object Exit: HomeConsumerIntent()
}

package com.tcc.alif.view.ui.consumer

sealed class HomeConsumerIntent{

    data class LoadHistoric(val idUser: String) : HomeConsumerIntent()
}

package com.tcc.alif.view.ui.administrator.home

sealed class HomeIntent {
    data class getQueuesBy(val idCompany : String) : HomeIntent()
}
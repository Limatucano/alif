package com.tcc.alif.view.ui.administrator.home

sealed class HomeIntent {
    data class GetQueuesBy(val idCompany : String) : HomeIntent()
}
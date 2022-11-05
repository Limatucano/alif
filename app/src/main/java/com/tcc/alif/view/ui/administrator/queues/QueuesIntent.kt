package com.tcc.alif.view.ui.administrator.queues

sealed class QueuesIntent{

    data class GetQueuesBy(
        val filter: String = "",
        val idCompany: String
    ): QueuesIntent()
}

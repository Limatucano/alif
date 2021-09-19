package com.tcc.alif.model.domain


data class MeusClientesFilaData(
        val nome_completo: String? = "",
        val numero_celular: Int? = null,
        val email: String? = "",
        val nascimento: String? = null,
)
package com.tcc.alif.data

import com.google.gson.annotations.SerializedName

data class MeusClientesFila(
    val response: List<MeusClientesFilaResponse>,
)

data class MeusClientesFilaResponse(
    @SerializedName("nome_completo") val nome_completo: String? = "",
    @SerializedName("numero_celular") val numero_celular: String? = "",
    @SerializedName("email") val email: String? = "",
    @SerializedName("nascimento") val nascimento: String? = "",
)
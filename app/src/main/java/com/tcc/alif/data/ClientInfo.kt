package com.tcc.alif.data

import com.google.gson.annotations.SerializedName

data class ClientInfo(
    @SerializedName("id_cliente") val id_cliente: Int? = null,
    @SerializedName("nome") val nome: String? = "",
    @SerializedName("sobrenome") val sobrenome: String? = "",
    @SerializedName("cpf") val cpf: String? = "",
    @SerializedName("nascimento") val nascimento: String? = null,
    @SerializedName("numero_celular") val numero_celular: String? = "",
    @SerializedName("email") val email: String? = "",
    @SerializedName("senha") val senha: String? = "",
)
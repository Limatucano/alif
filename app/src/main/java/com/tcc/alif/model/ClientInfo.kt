package com.tcc.alif.model

import com.google.gson.annotations.SerializedName

data class ClientInfo(
    @SerializedName("nome") val nome: String? = "",
    @SerializedName("sobrenome") val sobrenome: String?= "",
    @SerializedName("cpf") val cpf: String?= "",
    @SerializedName("nascimento") val nascimento: String?= "",
    @SerializedName("numero_celular") val numero_celular: String?= "",
    @SerializedName("ddd_celular") val ddd_celular: String?= "",
    @SerializedName("email") val email: String?= "",
    @SerializedName("senha") val senha: String?= "",
)
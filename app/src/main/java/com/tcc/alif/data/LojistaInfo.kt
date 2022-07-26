package com.tcc.alif.data

import com.google.gson.annotations.SerializedName

data class LojistaInfo(
    @SerializedName("id_lojista") val id_lojista: Int? = null,
    @SerializedName("nome_fantasia") val nome_fantasia: String? = "",
    @SerializedName("tipo_doc") val tipo_doc: String? = "",
    @SerializedName("doc") val doc: String? = "",
    @SerializedName("nome") val nome: String? = "",
    @SerializedName("ocupacao") val ocupacao: String? = "",
    @SerializedName("email") val email: String? = "",
    @SerializedName("senha") val senha: String? = "",
)
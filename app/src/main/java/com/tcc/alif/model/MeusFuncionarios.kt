package com.tcc.alif.model

import com.google.gson.annotations.SerializedName

data class MeusFuncionarios(
    val response: List<MeusFuncionariosResponse>,
)

data class MeusFuncionariosResponse(
    @SerializedName("nome_funcionario") val nome_funcionario: String? = "",
    @SerializedName("cod_funcionario") val cod_funcionario: Int? = null,
    @SerializedName("cargo") val cargo: String? = "",
    @SerializedName("id_lojista") val id_lojista: Int? = null,
    @SerializedName("cpf") val cpf: String? = "",
)
package com.tcc.alif.data

import com.google.gson.annotations.SerializedName

data class FuncionarioInfo(
    @SerializedName("nome_funcionario") val nome_funcionario: String? = "",
    @SerializedName("cargo") val cargo: String? = "",
    @SerializedName("id_lojista") val id_lojista: Int? = null,
    @SerializedName("cpf") val cpf: String? = "",
    @SerializedName("cod_funcionario") val cod_funcionario: Int? = null,
)
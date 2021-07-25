package com.tcc.alif.model

import com.google.gson.annotations.SerializedName

data class FilaInfo(
    @SerializedName("nome_da_fila") val nome_da_fila: String? = "",
    @SerializedName("quantidade_vagas") val quantidade_vagas: Int? = null,
    @SerializedName("horario_abertura") val horario_abertura: String? = "",
    @SerializedName("horario_fechamento") val horario_fechamento: String? = "",
    @SerializedName("tempo_medio") val tempo_medio: String? = "",
    @SerializedName("id_lojista") val id_lojista: String? = "",
    @SerializedName("id_fila") val id_fila: Int? = null,
)
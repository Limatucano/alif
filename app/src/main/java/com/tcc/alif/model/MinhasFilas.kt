package com.tcc.alif.model

import com.google.gson.annotations.SerializedName

data class MinhasFilasPost(
        @SerializedName("email") val email: String? = ""
)
data class MinhasFilas(
       val id_fila : Int,
       val response: List<MinhasFilasResponse>,
)

data class MinhasFilasResponse(
        val id_fila : String,
        val nome_da_fila: String,
        val quantidade_vagas:String,
        val horario_abertura:String,
        val horario_fechamento:String,
        val intervalo:String,
        val id_lojista:String
)
package com.tcc.alif.model

import com.google.gson.annotations.SerializedName

data class MessageRequest(
        @SerializedName("message")  val message : String? = ""
)

data class clienteAndFilaPost(
        @SerializedName("id_cliente") val id_cliente: String,
        @SerializedName("id_fila") val id_fila: String
)

data class MinhasFilasPost(
        @SerializedName("email") val email: String? = ""
)
data class FilaFiltradaPost(
        @SerializedName("nome") val nome: String?
)
data class MinhasFilas(
       val id_fila : Int,
       val response: List<MinhasFilasResponse>,
)

data class MinhasFilasResponse(
        val id_fila : String? = "",
        @SerializedName("nome_da_fila") val nome_da_fila: String? = "",
        val quantidade_vagas:String? = "",
        val horario_abertura:String? = "",
        val horario_fechamento:String? = "",
        var tempo_medio:String? = "",
        val id_lojista:String? = "",
        val nome_fantasia:String? = "",
        val quantidade_por_fila: String? = ""

)
package com.tcc.alif.model.domain


class MinhasFilasData(
        val nome_da_fila: String?,
        val id_fila : String?,
        val quantidade_vagas:String?,
        val horario_abertura:String?,
        val horario_fechamento:String?,
        val tempo_medio:String?,
        val id_lojista:String?,
        val nome_fantasia: String? = "",
        val quantidade_por_fila: String? = "",
        val primeirosClientes: List<String>? = null
)
package com.tcc.alif.data.model

import com.google.gson.annotations.SerializedName

data class Calls(
    val calls : List<Call>
)

data class Call(
    @SerializedName("posicao") val position : Int,
    @SerializedName("id_cliente") val idConsumer : Int,
    @SerializedName("nome_funcionario") val employeeName : String,
    @SerializedName("cargo") val employeeRole : String,
    @SerializedName("nome_cliente") val consumerName : String,
    @SerializedName("numero_celular") val cellphone : String,
    @SerializedName("data_nascimento") val birthDate : String,
    val cpf : String
)

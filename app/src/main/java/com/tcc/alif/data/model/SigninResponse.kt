package com.tcc.alif.data.model


data class SigninResponse(
    val name : String,
    val secondName : String,
    val cpf : String,
    val cellphone : String,
    val birthDate : String,
    val email : String,
    val priority : Boolean?,
    val idConsumer : String?
)

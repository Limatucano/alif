package com.tcc.alif.data.model

data class Calls(
    var calls : List<Call>,
    val quantity: Int
)

data class Call(
    val idConsumer : String,
    val employeeName : String,
    val employeeRole : String,
    val consumerName : String,
    val cellphone : String,
    val birthDate : String,
    val cpf : String
)

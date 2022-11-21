package com.tcc.alif.data.model.local

data class Employee(
    val name: String,
    val cpf: String,
    val uid: String,
    val cellphone: String,
    val statusRequest: EmployeeStatus? = null
)

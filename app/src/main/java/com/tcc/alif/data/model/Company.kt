package com.tcc.alif.data.model

data class Company(
    val idCompany : Int,
    val category : String,
    val tradeName : String,
    val ownerName : String,
    val telephone : String,
    val street : String,
    val district : String,
    val numberHouse : String,
    val city : String,
    val zipCode : String,
    val state : String,
    val addressContinued : String,
    val cnpj : String,
    val uuid : String
)
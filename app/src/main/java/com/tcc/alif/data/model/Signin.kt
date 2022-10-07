package com.tcc.alif.data.model

import com.tcc.alif.data.model.local.AccountType

data class Signin(
    val type : AccountType,
    val email : String,
    val password : String,
)

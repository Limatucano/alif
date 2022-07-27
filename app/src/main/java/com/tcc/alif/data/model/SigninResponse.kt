package com.tcc.alif.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SigninResponse(
    val name : String,
    val secondName : String,
    val cpf : String,
    val cellphone : String,
    val birthDate : String,
    val email : String,
    val priority : Boolean?,
    val idConsumer : String?,
    val idAdministrator : String?
) : Parcelable

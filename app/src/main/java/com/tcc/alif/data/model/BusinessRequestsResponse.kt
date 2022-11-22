package com.tcc.alif.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusinessRequestsResponse(
    val idCompany: String = "",
    val idUser: String = "",
    val status: String = "",
    val cnpj: String = "",
    val tradeName: String = ""
) : Parcelable

package com.tcc.alif.data.model

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("cep") val zipCode: String,
    @SerializedName("logradouro") val publicPlace: String,
    @SerializedName("complemento") val addressContinued: String,
    @SerializedName("bairro") val district: String,
    @SerializedName("localidade") val city: String,
    @SerializedName("uf") val uf: String
)

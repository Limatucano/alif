package com.tcc.alif.data.model

import com.google.gson.annotations.SerializedName

data class Companies(
    val companies: List<CompanyResponse>
)

data class CompanyResponse(
    @SerializedName("id_empresa") val idCompany : Int,
    @SerializedName("ocupacao") val category : String,
    @SerializedName("nome_fantasia") val tradeName : String,
    @SerializedName("nome_proprietario")val ownerName : String,
    @SerializedName("telefone") val telephone : String,
    @SerializedName("rua") val street : String,
    @SerializedName("bairro") val district : String,
    @SerializedName("numero") val numberHouse : String,
    @SerializedName("cidade") val city : String,
    @SerializedName("cep") val zipCode : String,
    @SerializedName("estado") val state : String,
    @SerializedName("complemento") val addressContinued : String,
    val cnpj : String,
    val uuid : String
)
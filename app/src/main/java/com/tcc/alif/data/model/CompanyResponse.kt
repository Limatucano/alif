package com.tcc.alif.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tcc.alif.data.util.emptyIfNull
import kotlinx.parcelize.Parcelize

data class Companies(
    val companies: List<CompanyResponse>
)

@Parcelize
data class CompanyResponse(
    val idCompany : String? = null,
    val category : String? = null,
    val tradeName : String? = null,
    val ownerName : String? = null,
    val telephone : String? = null,
    val street : String? = null,
    val district : String? = null,
    var role: String = "",
    val numberHouse : String? = null,
    val city : String? = null,
    val zipCode : String? = null,
    val state : String? = null,
    val addressContinued : String? = null,
    val cnpj : String? = null
) : Parcelable {

    fun toCompanyResponse(map: MutableMap<String, Any>) =
        CompanyResponse(
            idCompany = map["idCompany"].toString().emptyIfNull(),
            category = map["category"].toString().emptyIfNull(),
            tradeName = map["tradeName"].toString().emptyIfNull(),
            ownerName = map["ownerName"].toString().emptyIfNull(),
            telephone = map["telephone"].toString().emptyIfNull(),
            street = map["street"].toString().emptyIfNull(),
            district = map["district"].toString().emptyIfNull(),
            numberHouse = map["numberHouse"].toString().emptyIfNull(),
            city = map["city"].toString().emptyIfNull(),
            zipCode = map["zipcode"].toString().emptyIfNull(),
            state = map["state"].toString().emptyIfNull(),
            addressContinued = map["addressContinued"].toString().emptyIfNull(),
            cnpj = map["cnpj"].toString().emptyIfNull()
        )

    companion object{
        fun CompanyResponse.modelToMap() =
            mapOf(
                "category" to category,
                "tradeName" to tradeName,
                "ownerName" to ownerName,
                "telephone" to telephone,
                "street" to street,
                "district" to district,
                "numberHouse" to numberHouse,
                "city" to city,
                "zipCode" to zipCode,
                "state" to state,
                "addressContinued" to addressContinued,
                "cnpj" to cnpj
            )
    }
}
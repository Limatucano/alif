package com.tcc.alif.data.model

import android.os.Parcelable
import com.tcc.alif.data.util.emptyIfNull
import kotlinx.parcelize.Parcelize

@Parcelize
data class SigninResponse(
    val name : String = "",
    val cpf : String = "",
    val cellphone : String = "",
    val birthDate : String = "",
    val email : String = "",
    val priority : Boolean? = null,
    val isConsumer : Boolean? = null,
    val isAdministrator : Boolean? = null,
    val companies: List<String> = listOf(),
    val uid: String = ""
) : Parcelable{

    fun toSignResponse(map: MutableMap<String, Any>) =
        SigninResponse(
            name = map["name"].toString().emptyIfNull(),
            cpf = map["cpf"].toString().emptyIfNull(),
            cellphone = map["cellphone"].toString().emptyIfNull(),
            birthDate = map["birthdate"].toString().emptyIfNull(),
            email = map["email"].toString().emptyIfNull(),
            priority = map["priority"] as Boolean?,
            isConsumer = map["isConsumer"] as Boolean?,
            isAdministrator = map["isAdministrator"] as Boolean?,
            companies = map["companies"] as List<String>,
            uid = map["uid"].toString().emptyIfNull()
        )
}

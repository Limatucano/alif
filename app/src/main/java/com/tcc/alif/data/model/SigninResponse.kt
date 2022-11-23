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

    fun toSignResponse(map: MutableMap<String, Any>?) : SigninResponse{
        return if(map == null){
            SigninResponse()
        }else{
            SigninResponse(
                name = map["name"].toString().emptyIfNull(),
                cpf = map["cpf"].toString().emptyIfNull(),
                cellphone = map["cellphone"].toString().emptyIfNull(),
                birthDate = map["birthDate"].toString().emptyIfNull(),
                email = map["email"].toString().emptyIfNull(),
                priority = map["priority"] as Boolean?,
                isConsumer = map["isConsumer"] as Boolean?,
                isAdministrator = map["isAdministrator"] as Boolean?,
                companies = map["companies"] as List<String>,
                uid = map["uid"].toString().emptyIfNull()
            )
        }
    }

    companion object {
        fun SigninResponse.modelToMap() =
            mapOf(
                "name" to this.name,
                "cpf" to this.cpf,
                "email" to this.email,
                "birthDate" to this.birthDate,
                "cellphone" to this.cellphone
            )
    }
}

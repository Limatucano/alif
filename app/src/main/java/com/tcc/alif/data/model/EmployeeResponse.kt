package com.tcc.alif.data.model

import android.os.Parcelable
import com.tcc.alif.data.util.emptyIfNull
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeResponse(
    val idCompany: String = "",
    val idUser: String = "",
    val status: String = ""
) : Parcelable {

    fun toEmployeeResponse(map: MutableMap<String, Any>?) : EmployeeResponse{
        return if(map == null){
            EmployeeResponse()
        }else {
            EmployeeResponse(
                idCompany = map["idCompany"].toString().emptyIfNull(),
                idUser = map["idUser"].toString().emptyIfNull(),
                status = map["status"].toString().emptyIfNull()
            )
        }
    }

    companion object {
        const val WAITING_STATUS = "AGUARDANDO"
        const val REFUSED_STATUS = "RECUSADO"
        const val ACCEPTED_STATUS = "ACEITO"
    }
}

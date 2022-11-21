package com.tcc.alif.data.model

import android.os.Parcelable
import com.tcc.alif.data.model.local.EmployeeStatus
import com.tcc.alif.data.util.emptyIfNull
import com.tcc.alif.view.ui.administrator.employees.EmployeeAdapter
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

        fun EmployeeResponse.getStatus() : EmployeeStatus? =
            EmployeeStatus.values().firstOrNull { it.value == this.status }
    }
}

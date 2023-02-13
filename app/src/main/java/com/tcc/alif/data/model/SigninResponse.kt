package com.tcc.alif.data.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.tcc.alif.R
import com.tcc.alif.data.model.MyCompany.Companion.toMyCompany
import com.tcc.alif.data.util.Constants.PERMISSION_ADD_COMPANY
import com.tcc.alif.data.util.Constants.PERMISSION_ADD_EMPLOYEE
import com.tcc.alif.data.util.Constants.PERMISSION_ADD_QUEUE
import com.tcc.alif.data.util.Constants.PERMISSION_ADD_RESPONSIBLE_EMPLOYEE_QUEUE
import com.tcc.alif.data.util.Constants.PERMISSION_ADMIN_ROLE
import com.tcc.alif.data.util.Constants.PERMISSION_CATEGORY_QUEUE
import com.tcc.alif.data.util.Constants.PERMISSION_EDIT_COMPANY
import com.tcc.alif.data.util.Constants.PERMISSION_EDIT_QUEUE
import com.tcc.alif.data.util.Constants.PERMISSION_EMPLOYEE_ROLE
import com.tcc.alif.data.util.Constants.PERMISSION_REMOVE_EMPLOYEE
import com.tcc.alif.data.util.emptyIfNull
import kotlinx.parcelize.Parcelize

sealed class CompanyRole(
    @StringRes val text: Int,
    val value: String,
    val permissions: Map<String, Boolean>
){
    object AdminRole : CompanyRole(
        text = R.string.company_role_admin,
        value = PERMISSION_ADMIN_ROLE,
        permissions = mapOf(
            PERMISSION_EDIT_COMPANY to true,
            PERMISSION_ADD_COMPANY to true,
            PERMISSION_ADD_RESPONSIBLE_EMPLOYEE_QUEUE to true,
            PERMISSION_ADD_QUEUE to true,
            PERMISSION_EDIT_QUEUE to true,
            PERMISSION_ADD_EMPLOYEE to true,
            PERMISSION_REMOVE_EMPLOYEE to true,
            PERMISSION_CATEGORY_QUEUE to true
        )
    )

    object EmployeeRole : CompanyRole(
        text = R.string.company_role_employee,
        value = PERMISSION_EMPLOYEE_ROLE,
        permissions = mapOf(
            PERMISSION_EDIT_COMPANY to false,
            PERMISSION_ADD_COMPANY to false,
            PERMISSION_ADD_RESPONSIBLE_EMPLOYEE_QUEUE to false,
            PERMISSION_ADD_QUEUE to false,
            PERMISSION_EDIT_QUEUE to false,
            PERMISSION_ADD_EMPLOYEE to false,
            PERMISSION_REMOVE_EMPLOYEE to false,
            PERMISSION_CATEGORY_QUEUE to false
        )
    );

    companion object{
        fun getRoleByValue(value: String?): CompanyRole{
            return when(value){
                PERMISSION_EMPLOYEE_ROLE -> EmployeeRole
                PERMISSION_ADMIN_ROLE -> AdminRole
                else -> EmployeeRole
            }
        }
    }
}

@Parcelize
data class MyCompany(
    val companyId: String = "",
    val role: String = "",
) : Parcelable {

    companion object{
        fun toMyCompany(map: MutableMap<String, Any>?) : MyCompany{
            return if(map == null){
                MyCompany()
            } else {
                MyCompany(
                    companyId = map["companyId"].toString().emptyIfNull(),
                    role = map["role"].toString().emptyIfNull()
                )
            }
        }
    }
}


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
    val companies: List<MyCompany> = listOf(),
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
                companies = (map["companies"] as ArrayList<HashMap<String, Any>>).map { toMyCompany(it) },
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

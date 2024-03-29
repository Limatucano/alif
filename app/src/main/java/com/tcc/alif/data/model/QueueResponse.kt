package com.tcc.alif.data.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.google.firebase.Timestamp
import com.tcc.alif.R
import com.tcc.alif.data.util.emptyIfNull
import kotlinx.parcelize.Parcelize

@Parcelize
data class Queues(
    var queues : List<QueueResponse> = listOf()
) : Parcelable

@Parcelize
data class QueueResponse(
    val idQueue : String = "",
    val idCompany: String = "",
    var companyName: String = "",
    val name : String = "",
    val status : Int? = null,
    val openingTime : Timestamp = Timestamp(0,0),
    val closingTime : Timestamp = Timestamp(0,0),
    val quantity : Int? = null,
    val description : String? = null,
    val titleCategory : String = "",
    val employeeResponsible: String? = "",
    val averageTime : Int? = null,
    val employeeCreator : String? = "",
    var service : List<Service> = listOf(),
    val firstConsumers: List<Service> = listOf()
) : Parcelable{



    fun toQueueResponse(map: MutableMap<String, Any>?): QueueResponse{
        return if(map == null){
            QueueResponse()
        }else{
            QueueResponse(
                idQueue = map["idQueue"].toString().emptyIfNull(),
                idCompany = map["idCompany"].toString().emptyIfNull(),
                name = map["name"].toString().emptyIfNull(),
                status = getStatusStringRes(map["status"].toString()),
                openingTime = (map["openingTime"] as Timestamp),
                closingTime = (map["closingTime"] as Timestamp),
                quantity = map["quantity"].toString().toInt(),
                employeeResponsible = map["employeeResponsible"].toString().emptyIfNull(),
                description = map["description"].toString().emptyIfNull(),
                titleCategory = map["titleCategory"].toString().emptyIfNull(),
                averageTime = map["averageTime"].toString().toInt(),
                employeeCreator = map["employeeCreator"].toString().emptyIfNull(),
                service = (map["service"] as ArrayList<HashMap<String, Any>>).map { Service().toListService(it) }
            )
        }
    }

    companion object {
        const val OPENED_STATUS = "ABERTO"
        const val CLOSED_STATUS = "FECHADO"

        fun getStatusStringRes(status : String) : Int =
            when(status){
                OPENED_STATUS -> R.string.opened_status
                CLOSED_STATUS -> R.string.closed_status
                else -> R.string.pendent_status
            }

        fun getStatusByRes(@StringRes stringRes: Int?): String =
            when(stringRes){
                R.string.opened_status -> OPENED_STATUS
                R.string.closed_status -> CLOSED_STATUS
                else -> CLOSED_STATUS
            }
    }
}

@Parcelize
data class Service(
    var idService: String = "",
    var enrollmentTime: Timestamp = Timestamp(0,0),
    var status: String = "",
    var userId: String = "",
    var employeeResponsible: String = "",
    var name: String = ""
) : Parcelable {

    fun toListService(map: HashMap<String,Any>) =
        Service(
            idService = map["idService"].toString().emptyIfNull(),
            employeeResponsible = map["employeeResponsible"].toString().emptyIfNull(),
            enrollmentTime = (map["enrollmentTime"] as Timestamp),
            status = map["status"].toString().emptyIfNull(),
            userId = map["userId"].toString().emptyIfNull()
        )

    companion object{
        fun Service.modelToMap() = mapOf(
            "enrollmentTime" to enrollmentTime,
            "employeeResponsible" to employeeResponsible,
            "idService" to idService,
            "status" to status,
            "userId" to userId
        )
    }
}
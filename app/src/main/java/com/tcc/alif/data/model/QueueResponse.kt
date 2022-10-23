package com.tcc.alif.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.tcc.alif.R
import com.tcc.alif.data.util.DateFormats.NORMAL_DATE_FORMAT
import com.tcc.alif.data.util.emptyIfNull
import com.tcc.alif.data.util.toStringDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class Queues(
    var queues : List<QueueResponse>
) : Parcelable

@Parcelize
data class QueueResponse(
    val idQueue : String = "",
    val name : String = "",
    val status : Int? = null,
    val openingTime : Timestamp = Timestamp(0,0),
    val closingTime : Timestamp = Timestamp(0,0),
    val quantity : Int? = null,
    val description : String? = null,
    val titleCategory : String = "",
    val averageTime : Int? = null,
    val employeeCreator : String? = "",
    var service : List<Service> = listOf()
) : Parcelable{

    private fun getStatusStringRes(status : String) : Int =
        when(status){
            OPENED_STATUS -> R.string.opened_status
            CLOSED_STATUS -> R.string.closed_status
            else -> R.string.pendent_status
        }

    fun toQueueResponse(map: MutableMap<String, Any>?): QueueResponse{
        return if(map == null){
            QueueResponse()
        }else{
            QueueResponse(
                idQueue = map["idQueue"].toString().emptyIfNull(),
                name = map["name"].toString().emptyIfNull(),
                status = getStatusStringRes(map["status"].toString()),
                openingTime = (map["openingTime"] as Timestamp),
                closingTime = (map["closingTime"] as Timestamp),
                quantity = map["quantity"].toString().toInt(),
                description = map["description"].toString().emptyIfNull(),
                titleCategory = map["category"].toString().emptyIfNull(),
                averageTime = map["averageTime"].toString().toInt(),
                employeeCreator = "",
                service = (map["service"] as ArrayList<HashMap<String, Any>>).map { Service().toListService(it) }
            )
        }
    }

    companion object {
        const val OPENED_STATUS = "ABERTO"
        const val CLOSED_STATUS = "FECHADO"
    }
}

@Parcelize
data class Service(
    var enrollmentTime: Timestamp = Timestamp(0,0),
    var status: String = "",
    var userId: String = "",
    var name: String = ""
) : Parcelable {

    fun toListService(map: HashMap<String,Any>) =
        Service(
            enrollmentTime = (map["enrollmentTime"] as Timestamp),
            status = map["status"].toString().emptyIfNull(),
            userId = map["userId"].toString().emptyIfNull()
        )
}
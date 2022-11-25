package com.tcc.alif.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import kotlinx.parcelize.Parcelize

@Parcelize
data class QueueRequest(
    val idQueue : String,
    val idCompany: String,
    val name : String,
    val status : String,
    val openingTime : Timestamp?,
    val closingTime : Timestamp?,
    val quantity : Int,
    val description : String,
    val titleCategory : String,
    val averageTime : Int,
    val employeeResponsible: String,
    val employeeCreator : String,
    var service : List<Service> = listOf()
): Parcelable{

    companion object{
        fun QueueResponse.toQueueRequest(status: String) =
            QueueRequest(
                idQueue = this.idQueue,
                idCompany = this.idCompany,
                name = this.name,
                status = status,
                openingTime = this.openingTime,
                closingTime = this.closingTime,
                quantity = this.quantity ?: 0,
                employeeResponsible = this.employeeResponsible ?: EMPTY_STRING,
                description = this.description ?: EMPTY_STRING,
                titleCategory = this.titleCategory,
                averageTime = this.averageTime ?: 0,
                employeeCreator = this.employeeCreator ?: EMPTY_STRING,
                service = this.service
            )

        fun QueueRequest.modelToMap() =
            mapOf(
                "averageTime" to this.averageTime,
                "category" to this.titleCategory,
                "closingTime" to this.closingTime,
                "description" to this.description,
                "name" to this.name,
                "employeeResponsible" to this.employeeResponsible,
                "openingTime" to this.openingTime,
                "quantity" to this.quantity,
                "status" to this.status.uppercase()
            )
    }
}

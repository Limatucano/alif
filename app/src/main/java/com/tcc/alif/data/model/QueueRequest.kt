package com.tcc.alif.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
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
    val employeeCreator : String,
    var service : List<Service> = listOf()
): Parcelable

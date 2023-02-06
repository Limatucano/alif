package com.tcc.alif.data.model

import com.google.firebase.Timestamp

data class MyQueuesResponse(
    val idService: String,
    val idQueue: String,
    val queueName: String,
    val companyName: String?,
    val consumerPosition: Int,
    val estimatedTime: Int?,
    val idUser: String,
    val status: CallStatus,
    val employeeResponsible: String,
    val enrollmentTime: Timestamp
)

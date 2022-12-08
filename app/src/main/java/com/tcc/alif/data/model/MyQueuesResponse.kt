package com.tcc.alif.data.model

data class MyQueuesResponse(
    val idQueue: String,
    val queueName: String,
    val companyName: String?,
    val consumerPosition: Int,
    val estimatedTime: Int?,
    val idUser: String,
    val status: CallStatus
)

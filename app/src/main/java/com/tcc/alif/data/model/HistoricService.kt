package com.tcc.alif.data.model

import com.google.firebase.Timestamp

data class HistoricService(
    val queueName: String,
    val insertedDate: Timestamp,
    val status: CallStatus,
    val idUser: String
)

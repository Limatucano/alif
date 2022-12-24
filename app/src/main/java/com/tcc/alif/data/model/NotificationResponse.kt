package com.tcc.alif.data.model

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    val id: String?,
    val recipients: Int?,
    @SerializedName("external_id") val externalId: String?,
    val errors: List<String>?
)

package com.tcc.alif.data.model

import com.google.gson.annotations.SerializedName
import com.tcc.alif.BuildConfig

data class NotificationRequest(
    val contents: ContentData,
    val headings: ContentData,
    val name: String,
    @SerializedName("app_id") val appId: String = BuildConfig.ONE_SIGNAL_API_KEY,
    @SerializedName("channel_for_external_user_ids") val channelForExternalUserIds: String = "push",
    @SerializedName("include_external_user_ids") val includeExternalUserIds: List<String>
)

data class ContentData(
    val en: String
)

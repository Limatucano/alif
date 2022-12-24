package com.tcc.alif.data.api

import com.tcc.alif.BuildConfig
import com.tcc.alif.data.model.NotificationRequest
import com.tcc.alif.data.model.NotificationResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OneSignalService {

    @POST("notifications")
    suspend fun sendPushNotification(
        @Header("accept") accept: String = "application/json",
        @Header("content-type") contentType: String = "application/json",
        @Header("Authorization") apiKey: String = BuildConfig.ONE_SIGNAL_API,
        @Body notification: NotificationRequest
    ) : NotificationResponse
}
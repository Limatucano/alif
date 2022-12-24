package com.tcc.alif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tcc.alif.view.ui.MainActivity

class AlifFirebaseMessagingService : FirebaseMessagingService() {

    private var msgCounter = 0
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            showNotification(
                message = message.data["message"],
                title = message.data["title"]
            )
        } else if (message.notification != null) {
            showNotification(
                message = message.notification?.body,
                title = message.notification?.title
            )
        }
    }

    private fun showNotification(
        message: String?,
        title: String? = null
    ){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.icon_app))
            .setSmallIcon(R.mipmap.icon_app)
            .setContentTitle(if (title.isNullOrBlank()) getString(R.string.app_name) else title)
            .setContentText(message)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .setLights(ContextCompat.getColor(this, R.color.pink), 3000, 3000)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setChannelId(CHANNEL)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(NotificationChannel(
            CHANNEL,
            getString(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH
        ))
        notificationManager.notify(msgCounter, notificationBuilder.build())
        msgCounter += 1
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    companion object {
        const val CHANNEL = "FIREBASE"
    }
}
package com.example.pushes.notifications.notification_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.pushes.R

class PushNotificationManagerImpl(
    private val context: Context
) : PushNotificationManager {

    companion object {
        private const val DEFAULT_CHANNEL_ID = "default_notifications_chanel_id"
        private const val DEFAULT_CHANNEL_NAME = "General"
    }

    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        createNotificationChannel()
    }

    override fun showNotification(
        id: Int,
        title: String?,
        body: String?,
        intent: PendingIntent?
    ) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
            .setSmallIcon(R.drawable.img_bell)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(intent)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(id, notificationBuilder.build())
    }

    override fun hideNotification(id: Int) {
        notificationManager.cancel(id)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DEFAULT_CHANNEL_ID,
                DEFAULT_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}
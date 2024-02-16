package com.example.pushes.presentation.notifications.notification_manager

import android.app.PendingIntent

interface PushNotificationManager {
    fun showNotification(
        id: Int,
        title: String?,
        body: String?,
        intent: PendingIntent?
    )

    fun hideNotification(id: Int)
}
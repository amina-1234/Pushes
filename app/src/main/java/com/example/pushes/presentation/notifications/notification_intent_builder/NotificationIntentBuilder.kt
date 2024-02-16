package com.example.pushes.presentation.notifications.notification_intent_builder

import android.app.PendingIntent
import com.example.pushes.presentation.notifications.NotificationItem

interface NotificationIntentBuilder {
    fun buildIntent(notification: NotificationItem): PendingIntent
}
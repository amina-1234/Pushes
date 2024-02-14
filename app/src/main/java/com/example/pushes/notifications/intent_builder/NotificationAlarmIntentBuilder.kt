package com.example.pushes.notifications.intent_builder

import android.app.PendingIntent
import com.example.pushes.notifications.NotificationItem

interface NotificationAlarmIntentBuilder {
    fun buildIntent(notification: NotificationItem): PendingIntent
}
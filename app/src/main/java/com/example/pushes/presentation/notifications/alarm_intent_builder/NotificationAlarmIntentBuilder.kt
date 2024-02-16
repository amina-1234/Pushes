package com.example.pushes.presentation.notifications.alarm_intent_builder

import android.app.PendingIntent
import com.example.pushes.presentation.notifications.NotificationItem

interface NotificationAlarmIntentBuilder {
    fun buildIntent(notification: NotificationItem): PendingIntent
}
package com.example.pushes.presentation.notifications.notification_intent_builder

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.pushes.MainActivity
import com.example.pushes.presentation.notifications.NotificationItem
import com.example.pushes.presentation.notifications.NotificationType

class NotificationIntentBuilderImpl(
    private val context: Context
) : NotificationIntentBuilder {

    override fun buildIntent(notification: NotificationItem): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getBroadcast(
            context,
            generateRequestCode(notification),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun generateRequestCode(notification: NotificationItem): Int {
        return when (notification.type) {
            NotificationType.DAILY_REMINDER, NotificationType.DAILY_PLEDGE, NotificationType.DAILY_REVIEW ->
                notification.type.code

            else -> System.currentTimeMillis().toInt()
        }
    }

}
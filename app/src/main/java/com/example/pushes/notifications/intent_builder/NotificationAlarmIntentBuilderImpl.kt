package com.example.pushes.notifications.intent_builder

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.pushes.notifications.NotificationsReceiver
import com.example.pushes.notifications.NotificationItem
import com.example.pushes.notifications.NotificationType

class NotificationAlarmIntentBuilderImpl(
    private val context: Context
) : NotificationAlarmIntentBuilder {

    override fun buildIntent(notification: NotificationItem): PendingIntent {
        val intent = Intent(context, NotificationsReceiver::class.java).apply {
            putExtra(NotificationItem.KEY, notification)
        }
        return PendingIntent.getBroadcast(
            context,
            generateRequestCode(notification),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    // Set the same code for daily reminders of the same type
    // so that when a new notification time is set, the previous one is deleted
    private fun generateRequestCode(notification: NotificationItem): Int {
        return when (notification.type) {
            NotificationType.DAILY_REMINDER, NotificationType.DAILY_PLEDGE, NotificationType.DAILY_REVIEW ->
                notification.type.code

            else -> System.currentTimeMillis().toInt()
        }
    }

}
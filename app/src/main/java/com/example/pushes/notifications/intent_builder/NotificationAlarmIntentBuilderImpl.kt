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
        return PendingIntent.getBroadcast(
            context,
            generateRequestCode(notification),
            createIntentWithExtras(notification),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createIntentWithExtras(notification: NotificationItem): Intent {
        return Intent(context, NotificationsReceiver::class.java).apply {
            putExtra("id", notification.id)
            putExtra("title", notification.title)
            putExtra("body", notification.body)
        }
    }

    // Set the same code for daily reminders of the same type
    // so that when a new notification time is set, the previous one is deleted
    private fun generateRequestCode(notification: NotificationItem): Int {
        return when (notification.type) {
            NotificationType.DAILY_REMINDER, NotificationType.DAILY_PLEDGE, NotificationType.DAILY_REVIEW ->
                notification.type.ordinal

            else -> System.currentTimeMillis().toInt()
        }
    }

}
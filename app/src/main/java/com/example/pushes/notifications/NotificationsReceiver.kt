package com.example.pushes.notifications

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pushes.App
import com.example.pushes.MainActivity
import com.example.pushes.common.parcelable

class NotificationsReceiver : BroadcastReceiver() {

    private val notificationManager by lazy { App.instance.serviceLocator.notificationManager }
    private val notificationScheduler by lazy { App.instance.serviceLocator.notificationScheduler }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notification = intent?.parcelable<NotificationItem>(NotificationItem.KEY) ?: return

            showNotification(context, notification)

            if (notification.isRepeating) scheduleNextNotification(notification)
        }
    }

    private fun showNotification(context: Context, notification: NotificationItem) {
        notificationManager.showNotification(
            id = notification.hashCode(),
            title = notification.title,
            body = notification.body,
            intent = getNotificationIntent(context)
        )
    }

    private fun scheduleNextNotification(notification: NotificationItem) {
        notificationScheduler.schedule(notification)
    }

    private fun getNotificationIntent(context: Context): PendingIntent? {
        val notificationIntent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
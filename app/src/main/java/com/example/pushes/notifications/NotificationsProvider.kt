package com.example.pushes.notifications

import android.content.Context
import com.example.pushes.App
import com.example.pushes.R
import com.example.pushes.pushes.domain.TimeConstraints

class NotificationsProvider(
    private val context: Context
) {

    private val localStorage by lazy { App.instance.serviceLocator.localStorage }

    fun getScheduledNotifications(): List<NotificationItem> {
        return listOfNotNull(
            getNotification(NotificationType.DAILY_PLEDGE),
            getNotification(NotificationType.DAILY_REVIEW)
        )
    }

    private fun getNotification(type: NotificationType): NotificationItem? {
        val notificationTime = localStorage.getNotificationTime(notificationType = type)
        return notificationTime?.let { time ->
            NotificationItem(
                hour = time.hour,
                minute = time.minute,
                title = getTitle(type),
                body = getBody(type),
                type = type
            )
        }
    }

    fun getNotification(time: TimeConstraints, type: NotificationType): NotificationItem {
        return NotificationItem(
            hour = time.hour,
            minute = time.minute,
            title = getTitle(type),
            body = getBody(type),
            type = type
        )
    }

    private fun getTitle(type: NotificationType): String? {
        val resId = when (type) {
            NotificationType.DAILY_REMINDER -> R.string.notification_daily_reminder_title
            NotificationType.DAILY_PLEDGE -> R.string.notification_daily_pledge_title
            NotificationType.DAILY_REVIEW -> R.string.notification_daily_review_title
        }
        return context.getString(resId)
    }

    private fun getBody(type: NotificationType): String? {
        val resId = when (type) {
            NotificationType.DAILY_REMINDER -> R.string.notification_daily_reminder_body
            NotificationType.DAILY_PLEDGE -> R.string.notification_daily_pledge_body
            NotificationType.DAILY_REVIEW -> R.string.notification_daily_review_body
        }
        return context.getString(resId)
    }
}
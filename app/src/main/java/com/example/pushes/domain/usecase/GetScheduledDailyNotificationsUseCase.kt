package com.example.pushes.domain.usecase

import com.example.pushes.presentation.notifications.NotificationBuilder
import com.example.pushes.presentation.notifications.NotificationItem
import com.example.pushes.presentation.notifications.NotificationType
import com.example.pushes.data.preferences.LocalStorage

class GetScheduledDailyNotificationsUseCase(
    private val notificationBuilder: NotificationBuilder,
    private val localStorage: LocalStorage,
) {
    operator fun invoke(): List<NotificationItem> {
        return listOfNotNull(
            getNotification(NotificationType.DAILY_PLEDGE),
            getNotification(NotificationType.DAILY_REVIEW)
        )
    }

    private fun getNotification(type: NotificationType): NotificationItem? {
        val notificationTime = localStorage.getNotificationTime(type)
        return notificationTime?.let { time ->
            notificationBuilder.getNotification(time, type)
        }
    }
}
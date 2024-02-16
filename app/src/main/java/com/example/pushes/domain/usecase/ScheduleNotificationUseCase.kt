package com.example.pushes.domain.usecase

import com.example.pushes.presentation.notifications.NotificationItem
import com.example.pushes.presentation.notifications.notification_scheduler.NotificationScheduler

class ScheduleNotificationUseCase(
    private val notificationScheduler: NotificationScheduler,
) {
    operator fun invoke(notification: NotificationItem) {
        notificationScheduler.schedule(notification)
    }
}
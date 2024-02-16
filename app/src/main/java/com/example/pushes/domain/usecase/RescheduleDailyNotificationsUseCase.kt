package com.example.pushes.domain.usecase

import com.example.pushes.presentation.notifications.notification_scheduler.NotificationScheduler

class RescheduleDailyNotificationsUseCase(
    private val getScheduledDailyNotificationsUseCase: GetScheduledDailyNotificationsUseCase,
    private val notificationScheduler: NotificationScheduler,
) {
    operator fun invoke() {
        val scheduledNotifications = getScheduledDailyNotificationsUseCase()
        notificationScheduler.schedule(*scheduledNotifications.toTypedArray())
    }
}
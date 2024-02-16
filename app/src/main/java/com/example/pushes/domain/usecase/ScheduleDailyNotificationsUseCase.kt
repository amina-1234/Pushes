package com.example.pushes.domain.usecase

import com.example.pushes.presentation.notifications.NotificationType
import com.example.pushes.presentation.notifications.notification_scheduler.NotificationScheduler
import com.example.pushes.domain.TimeConstraints

class ScheduleDailyNotificationsUseCase(
    private val saveNotificationTimeUseCase: SaveNotificationTimeUseCase,
    private val getScheduledDailyNotificationsUseCase: GetScheduledDailyNotificationsUseCase,
    private val notificationScheduler: NotificationScheduler,
) {
    operator fun invoke(
        pledgeTime: TimeConstraints,
        reviewTime: TimeConstraints
    ) {
        saveNotificationTimeUseCase(NotificationType.DAILY_PLEDGE, pledgeTime)
        saveNotificationTimeUseCase(NotificationType.DAILY_REVIEW, reviewTime)

        val scheduledNotifications = getScheduledDailyNotificationsUseCase()
        notificationScheduler.schedule(*scheduledNotifications.toTypedArray())
    }
}
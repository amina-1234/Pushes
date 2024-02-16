package com.example.pushes.domain.usecase

import com.example.pushes.presentation.notifications.NotificationItem
import com.example.pushes.presentation.notifications.notification_intent_builder.NotificationIntentBuilder
import com.example.pushes.presentation.notifications.notification_manager.PushNotificationManager

class ShowNotificationUseCase(
    private val notificationManager: PushNotificationManager,
    private val notificationIntentBuilder: NotificationIntentBuilder
) {
    operator fun invoke(notification: NotificationItem) {
        notificationManager.showNotification(
            id = notification.hashCode(),
            title = notification.title,
            body = notification.body,
            intent = notificationIntentBuilder.buildIntent(notification)
        )
    }
}
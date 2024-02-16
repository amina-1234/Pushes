package com.example.pushes.presentation.notifications.notification_scheduler

import com.example.pushes.presentation.notifications.NotificationItem
import com.example.pushes.presentation.notifications.NotificationType

interface NotificationScheduler {
    fun schedule(vararg notifications: NotificationItem)
    fun cancel(notificationType: NotificationType)
}
package com.example.pushes.notifications.notification_scheduler

import com.example.pushes.notifications.NotificationItem
import com.example.pushes.notifications.NotificationType

interface NotificationScheduler {
    fun schedule(vararg notifications: NotificationItem)
    fun cancel(notificationType: NotificationType)
}
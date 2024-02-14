package com.example.pushes.notifications.notification_scheduler

import com.example.pushes.notifications.NotificationItem

interface NotificationScheduler {
    fun schedule(vararg notifications: NotificationItem)
    fun cancelAll()
}
package com.example.pushes.presentation.notifications.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pushes.App
import com.example.pushes.common.parcelable
import com.example.pushes.presentation.notifications.NotificationItem

class NotificationAlarmReceiver : BroadcastReceiver() {

    private val showNotificationUseCase by lazy { App.instance.serviceLocator.showNotificationUseCase }
    private val scheduleNotificationUseCase by lazy { App.instance.serviceLocator.scheduleNotificationUseCase }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notification = intent?.parcelable<NotificationItem>(NotificationItem.KEY) ?: return

        showNotificationUseCase(notification)
        if (notification.isRepeating) scheduleNotificationUseCase(notification)
    }
}
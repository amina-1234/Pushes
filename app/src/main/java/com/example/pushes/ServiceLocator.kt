package com.example.pushes

import android.content.Context
import com.example.pushes.notifications.NotificationsProvider
import com.example.pushes.notifications.intent_builder.NotificationAlarmIntentBuilder
import com.example.pushes.notifications.intent_builder.NotificationAlarmIntentBuilderImpl
import com.example.pushes.notifications.notification_manager.PushNotificationManager
import com.example.pushes.notifications.notification_manager.PushNotificationManagerImpl
import com.example.pushes.notifications.notification_scheduler.NotificationScheduler
import com.example.pushes.notifications.notification_scheduler.NotificationSchedulerImpl

class ServiceLocator(context: Context) {

    val notificationManager: PushNotificationManager by lazy {
        PushNotificationManagerImpl(context)
    }

    val notificationScheduler: NotificationScheduler by lazy {
        NotificationSchedulerImpl(context)
    }

    val notificationAlarmIntentBuilder: NotificationAlarmIntentBuilder by lazy {
        NotificationAlarmIntentBuilderImpl(context)
    }

    val notificationsProvider: NotificationsProvider by lazy {
        NotificationsProvider(context)
    }
}
package com.example.pushes

import android.content.Context
import android.content.SharedPreferences
import com.example.pushes.notifications.NotificationsProvider
import com.example.pushes.notifications.intent_builder.NotificationAlarmIntentBuilder
import com.example.pushes.notifications.intent_builder.NotificationAlarmIntentBuilderImpl
import com.example.pushes.notifications.notification_manager.PushNotificationManager
import com.example.pushes.notifications.notification_manager.PushNotificationManagerImpl
import com.example.pushes.notifications.notification_scheduler.NotificationScheduler
import com.example.pushes.notifications.notification_scheduler.NotificationSchedulerImpl
import com.example.pushes.preferences.LocalStorage
import com.example.pushes.preferences.SharedPreferencesStorage

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

    val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("pushes", Context.MODE_PRIVATE)
    }

    val localStorage: LocalStorage by lazy {
        SharedPreferencesStorage(sharedPreferences)
    }
}
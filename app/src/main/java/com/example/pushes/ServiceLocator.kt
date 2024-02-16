package com.example.pushes

import android.content.Context
import android.content.SharedPreferences
import com.example.pushes.presentation.notifications.NotificationBuilder
import com.example.pushes.presentation.notifications.alarm_intent_builder.NotificationAlarmIntentBuilder
import com.example.pushes.presentation.notifications.notification_intent_builder.NotificationIntentBuilder
import com.example.pushes.presentation.notifications.alarm_intent_builder.NotificationAlarmIntentBuilderImpl
import com.example.pushes.presentation.notifications.notification_intent_builder.NotificationIntentBuilderImpl
import com.example.pushes.presentation.notifications.notification_manager.PushNotificationManager
import com.example.pushes.presentation.notifications.notification_manager.PushNotificationManagerImpl
import com.example.pushes.presentation.notifications.notification_scheduler.NotificationScheduler
import com.example.pushes.presentation.notifications.notification_scheduler.NotificationSchedulerImpl
import com.example.pushes.data.preferences.LocalStorage
import com.example.pushes.data.preferences.SharedPreferencesStorage
import com.example.pushes.domain.usecase.GetScheduledDailyNotificationsUseCase
import com.example.pushes.domain.usecase.RescheduleDailyNotificationsUseCase
import com.example.pushes.domain.usecase.SaveNotificationTimeUseCase
import com.example.pushes.domain.usecase.ScheduleDailyNotificationsUseCase
import com.example.pushes.domain.usecase.ScheduleNotificationUseCase
import com.example.pushes.domain.usecase.ShowNotificationUseCase

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

    val notificationIntentBuilder: NotificationIntentBuilder by lazy {
        NotificationIntentBuilderImpl(context)
    }

    val notificationBuilder: NotificationBuilder by lazy {
        NotificationBuilder(context)
    }

    val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("pushes", Context.MODE_PRIVATE)
    }

    val localStorage: LocalStorage by lazy {
        SharedPreferencesStorage(sharedPreferences)
    }

    // Use Case
    val getScheduledDailyNotificationsUseCase: GetScheduledDailyNotificationsUseCase by lazy {
        GetScheduledDailyNotificationsUseCase(notificationBuilder, localStorage)
    }

    val saveNotificationTimeUseCase: SaveNotificationTimeUseCase by lazy {
        SaveNotificationTimeUseCase(localStorage)
    }

    val scheduleNotificationUseCase: ScheduleNotificationUseCase by lazy {
        ScheduleNotificationUseCase(notificationScheduler)
    }

    val scheduleDailyNotificationsUseCase: ScheduleDailyNotificationsUseCase by lazy {
        ScheduleDailyNotificationsUseCase(
            saveNotificationTimeUseCase,
            getScheduledDailyNotificationsUseCase,
            notificationScheduler
        )
    }

    val rescheduleDailyNotificationsUseCase: RescheduleDailyNotificationsUseCase by lazy {
        RescheduleDailyNotificationsUseCase(
            getScheduledDailyNotificationsUseCase,
            notificationScheduler
        )
    }

    val showNotificationUseCase: ShowNotificationUseCase by lazy {
        ShowNotificationUseCase(notificationManager, notificationIntentBuilder)
    }
}
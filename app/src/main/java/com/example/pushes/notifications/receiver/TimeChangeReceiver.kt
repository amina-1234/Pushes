package com.example.pushes.notifications.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.pushes.App
import com.example.pushes.common.canScheduleAlarm

class TimeChangeReceiver : BroadcastReceiver() {

    private val notificationsProvider by lazy { App.instance.serviceLocator.notificationsProvider }
    private val notificationScheduler by lazy { App.instance.serviceLocator.notificationScheduler }

    override fun onReceive(context: Context, intent: Intent) {
        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
        val validIntentActions = listOf(
            Intent.ACTION_TIMEZONE_CHANGED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_DATE_CHANGED
        )
        if (validIntentActions.contains(intent.action) && alarmManager.canScheduleAlarm) {
            val scheduledNotifications = notificationsProvider.getScheduledNotifications()
            notificationScheduler.schedule(*scheduledNotifications.toTypedArray())
        }
    }
}
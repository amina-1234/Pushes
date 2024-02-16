package com.example.pushes.presentation.notifications.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.pushes.App
import com.example.pushes.common.canScheduleAlarm

class ScheduleAlarmPermissionReceiver : BroadcastReceiver() {

    private val rescheduleDailyNotificationsUseCase by lazy {
        App.instance.serviceLocator.rescheduleDailyNotificationsUseCase
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED) return

        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
        if (alarmManager.canScheduleAlarm) rescheduleDailyNotificationsUseCase()
    }
}
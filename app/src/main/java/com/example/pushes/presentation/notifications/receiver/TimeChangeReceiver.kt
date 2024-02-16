package com.example.pushes.presentation.notifications.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.pushes.App
import com.example.pushes.common.canScheduleAlarm

class TimeChangeReceiver : BroadcastReceiver() {

    private val rescheduleDailyNotificationsUseCase by lazy {
        App.instance.serviceLocator.rescheduleDailyNotificationsUseCase
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (validIntentActions.contains(intent.action).not()) return

        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
        if (alarmManager.canScheduleAlarm) rescheduleDailyNotificationsUseCase()
    }

    companion object {
        val validIntentActions = listOf(
            Intent.ACTION_TIMEZONE_CHANGED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_DATE_CHANGED
        )
    }
}
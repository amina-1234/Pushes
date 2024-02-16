package com.example.pushes.presentation.notifications.notification_scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.pushes.App
import com.example.pushes.presentation.notifications.NotificationItem
import com.example.pushes.presentation.notifications.NotificationType
import com.example.pushes.presentation.notifications.receiver.NotificationAlarmReceiver
import com.example.pushes.presentation.notifications.alarm_intent_builder.NotificationAlarmIntentBuilder
import java.util.Calendar

class NotificationSchedulerImpl(
    private val context: Context
) : NotificationScheduler {

    private val intentBuilder: NotificationAlarmIntentBuilder by lazy {
        App.instance.serviceLocator.notificationAlarmIntentBuilder
    }

    private val alarmManager by lazy {
        ContextCompat.getSystemService(
            context,
            AlarmManager::class.java
        )
    }

    private val canScheduleAlarm: Boolean
        get() = !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager?.canScheduleExactAlarms() == false)


    override fun schedule(vararg notifications: NotificationItem) {
        if (canScheduleAlarm.not())
            throw IllegalStateException("The application has no rights to schedule notifications")

        notifications.forEach { notification -> schedule(notification) }
    }

    // Cancels all scheduled notifications of this type
    override fun cancel(notificationType: NotificationType) {
        val intent = Intent(context, NotificationAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationType.code,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager?.cancel(pendingIntent)
    }

    private fun schedule(notification: NotificationItem) {
        val notificationTime = getNextNotificationTime(notification)
        println("ahahah schedule: ${notificationTime.time}")
        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            notificationTime.timeInMillis,
            intentBuilder.buildIntent(notification)
        )
    }

    private fun getNextNotificationTime(notificationItem: NotificationItem): Calendar {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, notificationItem.hour)
            set(Calendar.MINUTE, notificationItem.minute)
            set(Calendar.SECOND, 0)
        }

        val currentTime = Calendar.getInstance()
        if (currentTime.after(calendar) || currentTime.compareTo(calendar) == 0) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return calendar
    }
}
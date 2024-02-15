package com.example.pushes.notifications.boot_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pushes.App

class BootReceiver: BroadcastReceiver() {

    private val notificationsProvider by lazy { App.instance.serviceLocator.notificationsProvider }
    private val notificationScheduler by lazy { App.instance.serviceLocator.notificationScheduler }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val scheduledNotifications = notificationsProvider.getScheduledNotifications()
            notificationScheduler.schedule(*scheduledNotifications.toTypedArray())
        }
    }
}
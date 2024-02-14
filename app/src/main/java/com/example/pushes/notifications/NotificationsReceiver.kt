package com.example.pushes.notifications

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pushes.App
import com.example.pushes.MainActivity

class NotificationsReceiver : BroadcastReceiver() {

    private val notificationManager by lazy { App.instance.serviceLocator.notificationManager }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val id = intent?.getStringExtra("id")
            val title = intent?.getStringExtra("title")
            val body = intent?.getStringExtra("body")
            val notificationIntent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            notificationManager.showNotification(
                id = id.hashCode(),
                title = title,
                body = body,
                intent = pendingIntent
            )
        }
    }
}
package com.example.pushes.presentation.notifications

import android.content.Context
import com.example.pushes.R
import com.example.pushes.domain.TimeConstraints

class NotificationBuilder(
    private val context: Context
) {

    fun getNotification(time: TimeConstraints, type: NotificationType): NotificationItem {
        return NotificationItem(
            hour = time.hour,
            minute = time.minute,
            title = getTitle(type),
            body = getBody(type),
            type = type
        )
    }

    private fun getTitle(type: NotificationType): String? {
        val resId = when (type) {
            NotificationType.DAILY_REMINDER -> R.string.notification_daily_reminder_title
            NotificationType.DAILY_PLEDGE -> R.string.notification_daily_pledge_title
            NotificationType.DAILY_REVIEW -> R.string.notification_daily_review_title
        }
        return context.getString(resId)
    }

    private fun getBody(type: NotificationType): String? {
        val resId = when (type) {
            NotificationType.DAILY_REMINDER -> R.string.notification_daily_reminder_body
            NotificationType.DAILY_PLEDGE -> R.string.notification_daily_pledge_body
            NotificationType.DAILY_REVIEW -> R.string.notification_daily_review_body
        }
        return context.getString(resId)
    }
}
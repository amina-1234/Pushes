package com.example.pushes.data.preferences

import android.content.SharedPreferences
import com.example.pushes.presentation.notifications.NotificationType
import com.example.pushes.domain.TimeConstraints

interface LocalStorage {
    fun saveNotificationTime(notificationType: NotificationType, time: TimeConstraints)
    fun getNotificationTime(notificationType: NotificationType): TimeConstraints?
}

class SharedPreferencesStorage(
    private val preferences: SharedPreferences
) : LocalStorage {

    companion object {
        private const val PLEDGE_TIME = "daily_pledge_time"
        private const val REVIEW_TIME = "daily_review_time"
    }

    override fun saveNotificationTime(notificationType: NotificationType, time: TimeConstraints) {
        getKey(notificationType)?.let {
            val totalMinutes = time.toTotalMinutes()
            preferences.edit().putInt(it, totalMinutes).apply()
        }
    }

    override fun getNotificationTime(notificationType: NotificationType): TimeConstraints? {
        return getKey(notificationType)?.let {
            val totalMinutes = preferences.getInt(it, -1)
            if (totalMinutes != -1) totalMinutes.toTimeConstraints() else null
        }
    }

    private fun getKey(notificationType: NotificationType): String? {
        return when (notificationType) {
            NotificationType.DAILY_PLEDGE -> PLEDGE_TIME
            NotificationType.DAILY_REVIEW -> REVIEW_TIME
            else -> null
        }
    }

    private fun TimeConstraints.toTotalMinutes(): Int {
        return hour * 60 + minute
    }

    private fun Int.toTimeConstraints(): TimeConstraints {
        val hour = this / 60
        val minute = this % 60
        return TimeConstraints(hour, minute)
    }
}
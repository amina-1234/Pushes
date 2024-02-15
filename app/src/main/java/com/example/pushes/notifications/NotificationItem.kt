package com.example.pushes.notifications

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationItem(
    val hour: Int,
    val minute: Int,
    val title: String?,
    val body: String?,
    val type: NotificationType,
) : Parcelable {

    companion object {
        const val KEY = "notification"
    }

    val isRepeating: Boolean
        get() = type in setOf(
            NotificationType.DAILY_REMINDER,
            NotificationType.DAILY_PLEDGE,
            NotificationType.DAILY_REVIEW
        )
}
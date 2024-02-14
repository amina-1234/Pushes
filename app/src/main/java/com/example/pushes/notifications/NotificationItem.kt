package com.example.pushes.notifications

data class NotificationItem(
    val hour: Int,
    val minute: Int,
    val title: String?,
    val body: String?,
    val type: NotificationType,
) {
    val id: String
        get() = type.name + hour + minute
}
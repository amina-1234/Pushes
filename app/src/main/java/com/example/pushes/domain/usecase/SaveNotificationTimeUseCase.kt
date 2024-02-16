package com.example.pushes.domain.usecase

import com.example.pushes.presentation.notifications.NotificationType
import com.example.pushes.data.preferences.LocalStorage
import com.example.pushes.domain.TimeConstraints

class SaveNotificationTimeUseCase(
    private val localStorage: LocalStorage
) {
    operator fun invoke(notificationType: NotificationType, time: TimeConstraints) {
        localStorage.saveNotificationTime(notificationType, time)
    }
}
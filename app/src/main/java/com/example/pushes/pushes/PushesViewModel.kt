package com.example.pushes.pushes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pushes.notifications.NotificationItem
import com.example.pushes.notifications.NotificationType
import com.example.pushes.App
import com.example.pushes.pushes.composable.PushesScreenClickListener
import com.example.pushes.pushes.domain.Error
import com.example.pushes.pushes.domain.Event
import com.example.pushes.pushes.domain.GoNext
import com.example.pushes.pushes.domain.RequestNotificationPermissions
import com.example.pushes.pushes.domain.ShowPermissionRationale
import com.example.pushes.pushes.domain.TimeConstraints
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PushesViewModel : ViewModel(), PushesScreenClickListener {

    private val scheduler by lazy { App.instance.serviceLocator.notificationScheduler }
    private val notificationsProvider by lazy { App.instance.serviceLocator.notificationsProvider }

    private val state = MutableStateFlow(PushesState.initial())
    val uiState: StateFlow<PushesState> = state

    private val events: MutableSharedFlow<Event> = MutableSharedFlow()
    val eventFlow: SharedFlow<Event> = events

    override fun onPledgeTimeChange(hour: Int, minute: Int) {
        state.value = state.value.copy(pledgeTime = TimeConstraints(hour, minute))
    }

    override fun onReviewTimeChange(hour: Int, minute: Int) {
        state.value = state.value.copy(reviewTime = TimeConstraints(hour, minute))
    }

    override fun onAllowNotificationClick() {
        viewModelScope.launch {
            events.emit(RequestNotificationPermissions)
        }
    }

    override fun onSkipStepClick() {
        viewModelScope.launch {
            events.emit(GoNext)
        }
    }

    fun onNotificationPermissionDenied() {
        viewModelScope.launch {
            events.emit(ShowPermissionRationale)
        }
    }

    fun onNotificationPermissionGranted() {
        viewModelScope.launch {
            try {
                state.value = state.value.copy(isAllowButtonLoading = true)

                // todo save time to prefs before scheduling
                val scheduledNotifications = notificationsProvider.getScheduledNotifications()
                scheduler.schedule(*scheduledNotifications.toTypedArray())

                events.emit(GoNext)
            } catch (e: Throwable) {
                events.emit(Error(e))
            } finally {
                state.value = state.value.copy(isAllowButtonLoading = false)
            }
        }
    }
}
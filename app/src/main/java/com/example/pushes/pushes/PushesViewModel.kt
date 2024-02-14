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

    private val state = MutableStateFlow(PushesState.initial())
    val uiState: StateFlow<PushesState> = state

    private val events: MutableSharedFlow<Event> = MutableSharedFlow()
    val eventFlow: SharedFlow<Event> = events

    private val scheduler by lazy { App.instance.serviceLocator.notificationScheduler }

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
        scheduler.cancelAll()
        println("ahahah skip step")
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

                // todo save times to prefs

                if (state.value.pledgeTime.time == state.value.reviewTime.time) {
                    val notification = NotificationItem(
                        hour = state.value.pledgeTime.hour,
                        minute = state.value.pledgeTime.minute,
                        title = "Time for check up!",
                        body = "la-la-la",
                        type = NotificationType.DAILY_REMINDER
                    )
                    scheduler.schedule(notification)
                } else {
                    val pledgeNotification = NotificationItem(
                        hour = state.value.pledgeTime.hour,
                        minute = state.value.pledgeTime.minute,
                        title = "Pledge",
                        body = "la-la-la",
                        type = NotificationType.DAILY_PLEDGE
                    )
                    val reviewNotification = NotificationItem(
                        hour = state.value.reviewTime.hour,
                        minute = state.value.reviewTime.minute,
                        title = "Review",
                        body = "la-la-la",
                        type = NotificationType.DAILY_REVIEW
                    )
                    scheduler.schedule(pledgeNotification, reviewNotification)
                }

            } catch (e: Throwable) {
                events.emit(Error(e))
            } finally {
                state.value = state.value.copy(isAllowButtonLoading = false)
            }
        }
    }
}
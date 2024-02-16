package com.example.pushes.presentation.pushes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pushes.App
import com.example.pushes.domain.Error
import com.example.pushes.domain.Event
import com.example.pushes.domain.GoNext
import com.example.pushes.domain.RequestNotificationPermissions
import com.example.pushes.domain.ShowPermissionRationale
import com.example.pushes.domain.TimeConstraints
import com.example.pushes.presentation.pushes.composable.PushesScreenClickListener
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PushesViewModel : ViewModel(), PushesScreenClickListener {

    private val scheduleDailyNotificationsUseCase by lazy {
        App.instance.serviceLocator.scheduleDailyNotificationsUseCase
    }

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

                scheduleDailyNotificationsUseCase(
                    pledgeTime = state.value.pledgeTime,
                    reviewTime = state.value.reviewTime
                )

                events.emit(GoNext)
            } catch (e: Throwable) {
                events.emit(Error(e))
            } finally {
                state.value = state.value.copy(isAllowButtonLoading = false)
            }
        }
    }
}
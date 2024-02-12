package com.example.pushes.pushes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pushes.pushes.composable.PushesScreenClickListener
import com.example.pushes.pushes.domain.Event
import com.example.pushes.pushes.domain.GoNext
import com.example.pushes.pushes.domain.RequestNotificationPermission
import com.example.pushes.pushes.domain.ShowPermissionRationale
import com.example.pushes.pushes.domain.TimeConstraints
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PushesViewModel(

) : ViewModel(), PushesScreenClickListener {

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
            events.emit(RequestNotificationPermission)
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

    }
}
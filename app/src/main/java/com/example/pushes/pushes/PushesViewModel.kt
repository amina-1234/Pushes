package com.example.pushes.pushes

import androidx.lifecycle.ViewModel
import com.example.pushes.pushes.composable.PushesScreenClickListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PushesViewModel(

) : ViewModel(), PushesScreenClickListener {

    private val state = MutableStateFlow(PushesState.initial())
    val uiState: StateFlow<PushesState> = state

    override fun onPledgeTimeChange(hour: Int, minute: Int) {
        state.value = state.value.copy(pledgeTime = TimeConstraints(hour, minute))
    }

    override fun onReviewTimeChange(hour: Int, minute: Int) {
        state.value = state.value.copy(reviewTime = TimeConstraints(hour, minute))
    }

    override fun onAllowNotificationClick() {

    }

    override fun onSkipStepClick() {

    }
}
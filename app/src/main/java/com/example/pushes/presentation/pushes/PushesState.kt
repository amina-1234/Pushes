package com.example.pushes.presentation.pushes

import com.example.pushes.domain.TimeConstraints

data class PushesState(
    val pledgeTime: TimeConstraints,
    val reviewTime: TimeConstraints,
    val isAllowButtonLoading: Boolean
) {
    companion object {
        fun initial() = PushesState(
            pledgeTime = DEFAULT_PLEDGE_TIME,
            reviewTime = DEFAULT_REVIEW_TIME,
            isAllowButtonLoading = false
        )
    }
}

private val DEFAULT_PLEDGE_TIME = TimeConstraints(hour = 8, minute = 0)
private val DEFAULT_REVIEW_TIME = TimeConstraints(hour = 20, minute = 0)

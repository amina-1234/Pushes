package com.example.pushes.domain

class TimeConstraints(
    val hour: Int,
    val minute: Int
) {
    val time: String
        get() = "%02d:%02d".format(hour, minute)
}
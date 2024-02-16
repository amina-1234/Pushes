package com.example.pushes.common

import android.app.AlarmManager
import android.os.Build

val AlarmManager?.canScheduleAlarm: Boolean
    get() = !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && this?.canScheduleExactAlarms() == false)

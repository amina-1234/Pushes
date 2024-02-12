package com.example.pushes

import android.content.Context
import com.example.pushes.pushes.PushesViewModel

class ServiceLocator(context: Context) {

    val pushesViewModel by lazy { PushesViewModel() }
}
package com.example.pushes

import android.app.Application

class App : Application() {

    lateinit var serviceLocator: ServiceLocator

    companion object {
        lateinit var instance: App private set
    }


    override fun onCreate() {
        super.onCreate()

        instance = this
        serviceLocator = ServiceLocator(applicationContext)
    }
}
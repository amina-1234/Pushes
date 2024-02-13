package com.example.pushes.pushes.domain

interface Event

class Error(e: Throwable) : Event
object RequestNotificationPermissions : Event
object ShowPermissionRationale : Event
object GoNext : Event
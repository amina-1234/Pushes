package com.example.pushes.pushes.domain

interface Event

object RequestNotificationPermission : Event
object ShowPermissionRationale : Event
object GoNext : Event
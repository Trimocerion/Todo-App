package com.wlczks.mvvm_todoapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {

        val channel = NotificationChannel(
            CounterNotificationService.NOTIFICATION_CHANNEL_ID,
            "Todo expiry notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "Used for todo expiry notification"

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)

    }


}
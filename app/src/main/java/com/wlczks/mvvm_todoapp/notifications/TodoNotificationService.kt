package com.wlczks.mvvm_todoapp.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.wlczks.mvvm_todoapp.MainActivity
import com.wlczks.mvvm_todoapp.R

class TodoNotificationService(
    private val context: Context,
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification() {


        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Todo Expiry")
            .setContentText("Your todo is about to expire:")
            .setSmallIcon(R.drawable.baseline_timer_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.baseline_timer_24,
                "View Todo",
                activityPendingIntent
            )
            .build()

        notificationManager.notify(1, notification)
    }


    companion object {
        const val NOTIFICATION_CHANNEL_ID = "todo_expiry_notification_channel"
    }
}

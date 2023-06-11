package com.wlczks.mvvm_todoapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.wlczks.mvvm_todoapp.data.Todo
import java.time.LocalDate

class TodoNotificationReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) {


        val todo: Todo? = intent.getParcelableExtra("todo", Todo::class.java)

        todo?.let {
            val currentDate = LocalDate.now()
            val todoDate = todo.date?.let { LocalDate.parse(it) }


            if (todoDate != null && currentDate.isAfter(todoDate.minusDays(1))) {
                val service = TodoNotificationService(context)
                service.showNotification()
            }
        }

    }
}
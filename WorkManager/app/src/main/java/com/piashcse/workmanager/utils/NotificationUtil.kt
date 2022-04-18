package com.piashcse.workmanager.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.piashcse.workmanager.MainActivity.Companion.CHANNEL_ID
import com.piashcse.workmanager.R

fun NotificationManager.sendNotification(context: Context, title: String = "Succeed", desc : String = "Worker is completed") {

    val NOTIFY_ID = 42633687

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(desc)
        .setSmallIcon(R.drawable.ic_baseline_computer_24)

    notify(NOTIFY_ID, notification.build())
}
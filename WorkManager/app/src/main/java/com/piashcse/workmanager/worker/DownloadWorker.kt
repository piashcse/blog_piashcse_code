package com.piashcse.workmanager.worker

import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.piashcse.workmanager.MainActivity.Companion.KEY_DOWNLOAD_DESC
import com.piashcse.workmanager.MainActivity.Companion.KEY_DOWNLOAD_TITLE
import com.piashcse.workmanager.utils.sendNotification

class DownloadWorker(private val context: Context, userParameters: WorkerParameters) :
    Worker(context, userParameters) {

    override fun doWork(): Result {
        try {

            val downloadTitle = inputData.getString(KEY_DOWNLOAD_TITLE) ?: return Result.failure()
            val downloadDesc = inputData.getString(KEY_DOWNLOAD_DESC) ?: return Result.failure()

            for (i in 0..300) {
                Log.i("piashcse", "Downloading image $i")
            }

            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

           notificationManager.sendNotification(context, downloadTitle, downloadDesc)

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}
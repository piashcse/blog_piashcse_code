package com.piashcse.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class FilterWorker(context: Context, userParameters: WorkerParameters) : Worker(context, userParameters) {

    override fun doWork(): Result {
        try {
            for (i in 0..300) {
                Log.i("piashcse", "Filtering image $i")
            }
            return Result.success()
        }catch (e: Exception) {
            return Result.failure()
        }
    }
}
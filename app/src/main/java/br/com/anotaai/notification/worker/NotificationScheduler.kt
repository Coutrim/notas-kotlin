package br.com.anotaai.notification.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
fun scheduleDailyNotification(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<DailyNotificationWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(Duration.ofMillis(calculateInitialDelay()))
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}

private fun calculateInitialDelay(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 18)
        set(Calendar.MINUTE, 10)
        set(Calendar.SECOND, 0)

        if (timeInMillis < System.currentTimeMillis()) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }
    return (calendar.timeInMillis - System.currentTimeMillis()).coerceAtLeast(0)
}


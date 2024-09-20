package br.com.anotaai.notification.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.anotaai.R

class DailyNotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }


    private fun showNotification() {
        try {
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "note_reminder_channel"
            val channelName = "Note Reminder"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = channelName
                val descriptionText = channelName
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(channelId, name, importance).apply {
                    description = descriptionText
                }

                val notificationManager: NotificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }


            var builder = NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notas")
                .setContentText("Opa, não esquece de escrever uma notinha hoje, hein?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            notificationManager.notify(1, builder.build())

            Log.d("NOTIFICAÇÃO", "JA ENVIOU NOTIFICACAO")
        } catch (e: Exception) {
            Log.e("NOTIFICAÇÃO", "Erro ao enviar notificação", e)
        }
    }


}

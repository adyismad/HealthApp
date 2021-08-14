package edu.bu.metcs.myproject

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.app.NotificationCompat

object FrainerUtils {

    const val LOGGED_USER = "logged_user"

    fun scheduleNotification(notification: Notification, delay: Int, context: Context) {
        val notificationIntent = Intent(context, MyNotificationPublisher::class.java)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?)
        alarmManager?.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
    }

    fun getNotification(content: String, context: Context, color: Int): Notification {
        val builder: NotificationCompat.Builder = context.let { NotificationCompat.Builder(it, MainActivity.default_notification_channel_id) }
        builder.setContentTitle("Frainer Invite")
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setAutoCancel(true)
        builder.color = color
        builder.setChannelId(MainActivity.NOTIFICATION_CHANNEL_ID)
        return builder.build()
    }

}
package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat

class NotificationHelper(private val context: Context) {

    private val channelId = "default_channel"  // Unique channel ID
    private val channelName = "Default Notifications"  // Channel name for API 26+ (Oreo and above)

    // Method to send notification
    fun sendNotification(title: String, message: String) {
        // Create the NotificationChannel if running on Oreo or higher

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = "Channel for general notifications"

            // Get the NotificationManager system service
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_notification_clear_all)  // Set an icon for the notification
            .setContentTitle(title)  // Set the notification title
            .setContentText(message)  // Set the notification message
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)  // Set notification priority
            .setAutoCancel(true)  // Automatically remove notification when clicked
            .build()

        // Get the NotificationManager system service
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Send the notification (ID is used to update the notification later if needed)
        notificationManager.notify(1, notification)
    }
}

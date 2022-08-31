package com.example.dispatchbuddy.common

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants.CHANNEL_ID
import com.example.dispatchbuddy.common.Constants.NOTIFICATION_ID
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.RiderActivity

class WorkerClass(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val context = context

    override fun doWork(): Result {
        setUpNotification(context)
        return Result.success()
    }

    companion object {
        fun setUpNotification(context: Context) {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, RiderActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Delivery")
                .setContentText("New Delivery Request")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("New Delivery Request")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define

                notify(NOTIFICATION_ID, notificationBuilder.build())
            }
        }
    }
    }
package com.example.apptrackingmodual.Service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.apptrackingmodual.MainActivity
import com.example.apptrackingmodual.R
import com.example.apptrackingmodual.SharedPreference.AppTracePreference
import com.example.apptrackingmodual.Utilities.Constants
import com.example.apptrackingmodual.Utilities.Utilities
import java.lang.String
import java.text.ParseException
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.Exception
import kotlin.Int
import kotlin.Throws
import kotlin.toString


class AppTrackingService : Service() {

    private var timer: ScheduledExecutorService? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        println("service start--------------------------------------------")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        println("service start onStartCommand-----------------------------")
        createNotificationChannel()
        NotificationService()

        return START_NOT_STICKY
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel: NotificationChannel =
                NotificationChannel(Constants.CHANNEL_ID, "AppTrack", NotificationManager.IMPORTANCE_DEFAULT)
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    fun NotificationService () {

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 2, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification: Notification = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setContentTitle("AppTrack Service")
            .setContentText("AppTrack trace.")
            .setSmallIcon(R.drawable.ic_apps_black_24dp)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(20, notification)
    }

}
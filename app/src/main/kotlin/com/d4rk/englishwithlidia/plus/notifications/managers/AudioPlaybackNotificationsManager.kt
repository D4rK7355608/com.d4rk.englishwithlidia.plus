package com.d4rk.englishwithlidia.plus.notifications.managers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.common.Player
import androidx.media3.ui.PlayerNotificationManager
import com.d4rk.englishwithlidia.plus.R

class AudioPlaybackNotificationsManager(private val context: Context) {
    private val channelId = "audio_playback_channel"
    private val notificationId = 1
    private var playerNotificationManager: PlayerNotificationManager? = null

    fun show(player: Player) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ensureChannel(notificationManager)
        }
        if (playerNotificationManager == null) {
            playerNotificationManager = PlayerNotificationManager.Builder(
                context,
                notificationId,
                channelId
            ).setSmallIconResourceId(R.drawable.ic_notification_important)
                .build().apply {
                    setPlayer(player)
                }
        } else {
            playerNotificationManager?.setPlayer(player)
        }
    }

    fun hide() {
        playerNotificationManager?.setPlayer(null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun ensureChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            channelId,
            context.getString(R.string.audio_playback_notifications),
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}

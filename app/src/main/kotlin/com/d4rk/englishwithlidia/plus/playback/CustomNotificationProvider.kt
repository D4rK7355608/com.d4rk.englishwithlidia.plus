package com.d4rk.englishwithlidia.plus.playback

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaSession

class CustomNotificationProvider(context: Context) : DefaultMediaNotificationProvider(context) {
    override fun getMediaButtons(
        session: MediaSession,
        controllerInfo: MediaSession.ControllerInfo
    ): List<NotificationCompat.Action> {
        val actions = mutableListOf<NotificationCompat.Action>()
        actions += createPlayPauseAction(session, controllerInfo)
        actions += createSkipNextAction(session, controllerInfo)
        return actions
    }
}

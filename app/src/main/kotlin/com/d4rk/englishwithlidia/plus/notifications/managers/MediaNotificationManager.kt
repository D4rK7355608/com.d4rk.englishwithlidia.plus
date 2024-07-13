package com.d4rk.englishwithlidia.plus.notifications.managers

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerNotificationManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.constants.media.MediaNotificationConstants
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@UnstableApi
class MediaNotificationManager( // FIXME: Class "MediaNotificationManager" is never used
    private val context: Context,
    sessionToken: SessionToken,
    private val player: Player,
    notificationListener: PlayerNotificationManager.NotificationListener
) {
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private val notificationManager: PlayerNotificationManager

    private val glideOptions: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    init {
        val mediaController = MediaController.Builder(context, sessionToken).buildAsync()

        notificationManager = PlayerNotificationManager.Builder(
            context,
            MediaNotificationConstants.NOW_PLAYING_NOTIFICATION_ID,
            MediaNotificationConstants.NOW_PLAYING_CHANNEL_ID
        )
            .setChannelNameResourceId(R.string.media_notification_channel)
            .setChannelDescriptionResourceId(R.string.media_notification_channel_description)
            .setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            .setNotificationListener(notificationListener)
            .setSmallIconResourceId(R.drawable.ic_notification_important)
            .build()
            .apply {
                setPlayer(player)
                setUseRewindAction(true)
                setUseFastForwardAction(true)
                setUseRewindActionInCompactView(true)
                setUseFastForwardActionInCompactView(true)
            }
    }

    fun hideNotification() { // FIXME: Function "hideNotification" is never used
        notificationManager.setPlayer(null)
    }

    fun showNotificationForPlayer(player: Player) { // FIXME: Function "showNotificationForPlayer" is never used
        notificationManager.setPlayer(player)
    }

    private inner class DescriptionAdapter(private val controller: ListenableFuture<MediaController>) :
        PlayerNotificationManager.MediaDescriptionAdapter {

        var currentIconUri: Uri? = null
        var currentBitmap: Bitmap? = null

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            val sessionActivityPendingIntent =
                context.packageManager?.getLaunchIntentForPackage(context.packageName)
                    ?.let { sessionIntent ->
                        PendingIntent.getActivity(
                            context,
                            MediaNotificationConstants.SESSION_INTENT_REQUEST_CODE,
                            sessionIntent,
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    }
            return sessionActivityPendingIntent
        }

        override fun getCurrentContentText(player: Player) = ""

        override fun getCurrentContentTitle(player: Player) = controller.get().mediaMetadata.title.toString()

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            val iconUri = controller.get().mediaMetadata.artworkUri
            return if (currentIconUri != iconUri || currentBitmap == null) {
                currentIconUri = iconUri
                serviceScope.launch {
                    currentBitmap = iconUri?.let {
                        resolveUriAsBitmap(it)
                    }
                    currentBitmap?.let { callback.onBitmap(it) }
                }
                null
            } else {
                currentBitmap
            }
        }

        private suspend fun resolveUriAsBitmap(uri: Uri): Bitmap? {
            return withContext(Dispatchers.IO) {
                // Block on downloading artwork.
                Glide.with(context).applyDefaultRequestOptions(glideOptions) // FIXME: Unresolved reference: glideOptions
                    .asBitmap()
                    .load(uri)
                    .submit(MediaNotificationConstants.NOTIFICATION_LARGE_ICON_SIZE, MediaNotificationConstants.NOTIFICATION_LARGE_ICON_SIZE)
                    .get()
            }
        }
    }
}
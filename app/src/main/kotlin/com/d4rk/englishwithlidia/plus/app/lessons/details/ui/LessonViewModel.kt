package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.copyData
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.setLoading
import com.d4rk.android.libs.apptoolkit.core.ui.base.ScreenViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.action.LessonAction
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.action.LessonEvent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.core.utils.extensions.await
import com.d4rk.englishwithlidia.plus.playback.AudioPlaybackService
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LessonViewModel(
    private val application: Application,
    private val getLessonUseCase: GetLessonUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : ScreenViewModel<UiLessonScreen, LessonEvent, LessonAction>(
    initialState = UiStateScreen(screenState = ScreenState.IsLoading(), data = UiLessonScreen())
) {

    private var controllerFuture: ListenableFuture<MediaController>? = null
    private var player: Player? = null

    init {
        launch {
            val context = application
            val intent = Intent(context, AudioPlaybackService::class.java)
            ContextCompat.startForegroundService(context, intent)
            val sessionToken = SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))
            controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
            player = controllerFuture?.await()
            player?.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    screenState.copyData { copy(isPlaying = isPlaying) }
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        val duration = player?.duration ?: 0L
                        screenState.copyData { copy(playbackDuration = duration) }
                    }
                }
            })
        }
    }

    fun getLesson(lessonId: String) {
        onEvent(LessonEvent.FetchLesson(lessonId))
    }

    private fun fetchLesson(lessonId: String) {
        launch(context = dispatcherProvider.io) {
            screenState.setLoading<UiLessonScreen>()
            val lesson = getLessonUseCase(lessonId)
            withContext(dispatcherProvider.main) {
                screenState.update { current ->
                    current.copy(screenState = ScreenState.Success(), data = lesson)
                }
            }
        }
    }

    fun preparePlayer(audioUrl: String, title: String) {
        launch {
            controllerFuture?.await()?.let { controller ->
                val mediaItem = MediaItem.Builder()
                    .setUri(Uri.parse(audioUrl))
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setTitle(title)
                            .build()
                    )
                    .build()
                controller.setMediaItem(mediaItem)
                controller.prepare()
                controller.playWhenReady = false
            }
            startPositionUpdateJob()
        }
    }

    override fun onEvent(event: LessonEvent) {
        when (event) {
            is LessonEvent.FetchLesson -> fetchLesson(event.lessonId)
        }
    }

    fun playPause() {
        player?.let { controller ->
            if (controller.isPlaying) {
                controller.pause()
            } else {
                controller.playWhenReady = true
            }
        }
    }

    fun seekTo(position: Long) {
        player?.seekTo(position)
    }

    private fun startPositionUpdateJob() {
        launch(context = dispatcherProvider.default) {
            while (true) {
                val currentPosition = withContext(dispatcherProvider.main) {
                    player?.currentPosition ?: 0L
                }
                withContext(dispatcherProvider.main) {
                    screenState.copyData { copy(playbackPosition = currentPosition) }
                }
                delay(timeMillis = 100)
                val isPlaying = withContext(dispatcherProvider.main) {
                    player?.isPlaying == true
                }
                if (!isPlaying) {
                    break
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        controllerFuture?.let { MediaController.releaseFuture(it) }
    }
}

package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import android.content.ComponentName
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.d4rk.englishwithlidia.plus.playback.AudioPlaybackService
import com.google.common.util.concurrent.ListenableFuture
import com.d4rk.englishwithlidia.plus.core.utils.extensions.await
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LessonViewModel(
    application: Application,
    private val getLessonUseCase: GetLessonUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : BaseViewModel(application) {

    private val _uiState = MutableStateFlow(UiLessonScreen())
    val uiState: StateFlow<UiLessonScreen> = _uiState.asStateFlow()

    private var controllerFuture: ListenableFuture<MediaController>? = null
    private var player: Player? = null

    init {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val intent = Intent(context, AudioPlaybackService::class.java)
            ContextCompat.startForegroundService(context, intent)
            val sessionToken = SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))
            controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
            player = controllerFuture?.await()
            player?.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    updateUiState { copy(isPlaying = isPlaying) }
                }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_READY) {
                            val duration = player?.duration ?: 0L
                            updateUiState { copy(playbackDuration = duration) }
                        }
                    }
                })
        }
    }

    fun getLesson(lessonId: String) {
        viewModelScope.launch(coroutineExceptionHandler + dispatcherProvider.io) {
            showLoading()
            val lesson = getLessonUseCase(lessonId)
            withContext(dispatcherProvider.main) {
                _uiState.value = lesson
                hideLoading()
            }
        }
    }

    fun preparePlayer(audioUrl: String, title: String) {
        viewModelScope.launch {
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
        viewModelScope.launch(dispatcherProvider.default) {
            while (true) {
                val currentPosition = withContext(dispatcherProvider.main) {
                    player?.currentPosition ?: 0L
                }
                withContext(dispatcherProvider.main) {
                    updateUiState { copy(playbackPosition = currentPosition) }
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

    private fun updateUiState(update: UiLessonScreen.() -> UiLessonScreen) {
        _uiState.value = _uiState.value.update()
    }

    override fun onCleared() {
        super.onCleared()
        controllerFuture?.let { MediaController.releaseFuture(it) }
    }
}

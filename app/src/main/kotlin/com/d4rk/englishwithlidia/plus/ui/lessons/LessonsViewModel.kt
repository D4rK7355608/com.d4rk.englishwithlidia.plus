package com.d4rk.englishwithlidia.plus.ui.lessons

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.d4rk.englishwithlidia.plus.data.model.ui.lessons.UiLessonsAsset
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LessonsViewModel(application: Application, lessonDetails: UiLessonsAsset?) : AndroidViewModel(application) {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackPosition = MutableStateFlow(0L)
    val playbackPosition: StateFlow<Long> = _playbackPosition.asStateFlow()

    private val _playbackDuration = MutableStateFlow(0L)
    val playbackDuration: StateFlow<Long> = _playbackDuration.asStateFlow()

    var lessonDetails: UiLessonsAsset? by mutableStateOf(lessonDetails)

    private var player: Player? = null

    init {
        viewModelScope.launch {
            player = ExoPlayer.Builder(getApplication()).build()
        }
    }

    fun preparePlayer(audioUrl: String) {
        viewModelScope.launch {
            player?.release()

            val audioUri = Uri.parse(audioUrl)

            player = ExoPlayer.Builder(getApplication()).build().apply {
                setMediaItem(MediaItem.fromUri(audioUri))
                prepare()
                playWhenReady = false

                addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        _isPlaying.value = isPlaying
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_READY) {
                            _playbackDuration.value = duration
                        }
                    }

                    override fun onPositionDiscontinuity(
                        oldPosition: Player.PositionInfo,
                        newPosition: Player.PositionInfo,
                        reason: Int
                    ) {
                        _playbackPosition.value = currentPosition
                    }
                })
            }
            startPositionUpdateJob()
        }
    }

    fun playPause() {
        player?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                it.playWhenReady = true
            }
        }
    }

    fun seekTo(position: Long) {
        player?.seekTo(position)
    }

    private fun startPositionUpdateJob() {
        viewModelScope.launch {
            while (true) {
                _playbackPosition.value = player?.currentPosition ?: 0L
                delay(100)
                if (player?.isPlaying == false) {
                    break
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        player?.release()
    }
}
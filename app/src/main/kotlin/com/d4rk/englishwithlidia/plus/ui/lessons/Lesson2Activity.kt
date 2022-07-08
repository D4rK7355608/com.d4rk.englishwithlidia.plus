package com.d4rk.englishwithlidia.plus.ui.lessons
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.databinding.ActivityLesson2Binding
import com.kieronquinn.monetcompat.app.MonetCompatActivity
import kotlin.math.ceil
import kotlin.math.roundToInt
class Lesson2Activity: MonetCompatActivity(), Runnable  {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var wasPlaying = false
    private var startPoint = 0
    private lateinit var binding: ActivityLesson2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLesson2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lesson2PlayButton.setOnClickListener { playSong() }
        binding.lesson2Seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                startPoint = seekBar.progress
            }
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromTouch: Boolean) {
                ceil((progress / 1000f).toDouble()).toInt()
                val percent = progress / seekBar.max.toDouble()
                val offset = seekBar.thumbOffset
                val seekWidth = seekBar.width
                (percent * (seekWidth - 2 * offset)).roundToInt()
                if (progress > 0 && !mediaPlayer.isPlaying) {
                    clearMediaPlayer()
                    binding.lesson2PlayButton.setImageDrawable(ContextCompat.getDrawable(this@Lesson2Activity, R.drawable.ic_media_play))
                    this@Lesson2Activity.binding.lesson2Seekbar.progress = 0
                }
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.seekTo(seekBar.progress)
                }
            }
        })
    }
    private fun playSong() {
        try {
            if (mediaPlayer.isPlaying) {
                clearMediaPlayer()
                binding.lesson2Seekbar.progress = 0
                wasPlaying = true
                binding.lesson2PlayButton.setImageDrawable(ContextCompat.getDrawable(this@Lesson2Activity, R.drawable.ic_play))
            }
            if (! wasPlaying) {
                binding.lesson2PlayButton.setImageDrawable(ContextCompat.getDrawable(this@Lesson2Activity, R.drawable.ic_pause))
                val descriptor = assets.openFd("lesson2.ogg")
                mediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                descriptor.close()
                mediaPlayer.prepare()
                mediaPlayer.setVolume(0.5f, 0.5f)
                mediaPlayer.isLooping = false
                binding.lesson2Seekbar.max = mediaPlayer.duration
                mediaPlayer.start()
                Thread(this).start()
            }
            wasPlaying = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun run() {
        var currentPosition = mediaPlayer.currentPosition
        val total = mediaPlayer.duration
        while (mediaPlayer.isPlaying && currentPosition < total) {
            currentPosition = try {
                Thread.sleep(1000)
                mediaPlayer.currentPosition
            } catch (e: Exception) {
                return
            }
            binding.lesson2Seekbar.progress = currentPosition
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            clearMediaPlayer()
        }
    }
    private fun clearMediaPlayer() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}
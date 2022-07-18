package com.d4rk.englishwithlidia.plus.ui.lessons
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.databinding.ActivityLesson1Binding
import kotlin.math.ceil
import kotlin.math.roundToInt
class Lesson1Activity: AppCompatActivity(), Runnable  {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var wasPlaying = false
    private var startPoint = 0
    private lateinit var binding: ActivityLesson1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLesson1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lesson1PlayButton.setOnClickListener {
            playSong()
        }
        binding.lesson1Seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
                    onDestroy()
                    binding.lesson1PlayButton.setImageDrawable(ContextCompat.getDrawable(this@Lesson1Activity, R.drawable.ic_media_play))
                    this@Lesson1Activity.binding.lesson1Seekbar.progress = 0
                }
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.seekTo(binding.lesson1Seekbar.progress)
                }
            }
        })
    }
    private fun playSong() {
        try {
            if (mediaPlayer.isPlaying) {
                onDestroy()
                binding.lesson1Seekbar.progress = 0
                wasPlaying = true
                binding.lesson1PlayButton.setImageDrawable(ContextCompat.getDrawable(this@Lesson1Activity, R.drawable.ic_play))
            }
            if (!wasPlaying) {
                binding.lesson1PlayButton.setImageDrawable(ContextCompat.getDrawable(this@Lesson1Activity, R.drawable.ic_pause))
                val descriptor = assets.openFd("lesson1.ogg")
                mediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                descriptor.close()
                mediaPlayer.prepare()
                mediaPlayer.setVolume(0.5f, 0.5f)
                mediaPlayer.isLooping = false
                binding.lesson1Seekbar.max = mediaPlayer.duration
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
            binding.lesson1Seekbar.progress = currentPosition
        }
    }
    override fun onDestroy() {
        mediaPlayer.stop()
        super.onDestroy()
    }
}
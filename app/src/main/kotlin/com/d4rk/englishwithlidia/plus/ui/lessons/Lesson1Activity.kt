package com.d4rk.englishwithlidia.plus.ui.lessons
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.databinding.ActivityLesson1Binding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import me.zhanghai.android.fastscroll.FastScrollerBuilder
class Lesson1Activity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var binding: ActivityLesson1Binding
    private val handler = Handler(Looper.getMainLooper())
    private val seekbarUpdater = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    val currentPosition = it.currentPosition
                    binding.seekbar.progress = currentPosition
                    handler.postDelayed(this, 1000)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLesson1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        FastScrollerBuilder(binding.scrollView).useMd2Style().build()
        MobileAds.initialize(this)
        binding.adView.loadAd(AdRequest.Builder().build())
        mediaPlayer = MediaPlayer()
        binding.buttonPlay.setOnClickListener { playSong() }
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    private fun playSong() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                binding.buttonPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_media_play))
            } else {
                if (!it.isPlaying) {
                    try {
                        assets.openFd("lesson1.ogg").use { descriptor ->
                            it.reset()
                            it.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                            it.prepare()
                            it.setVolume(0.5f, 0.5f)
                            it.isLooping = false
                            binding.seekbar.max = it.duration
                            it.start()
                            binding.buttonPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause))
                            handler.post(seekbarUpdater)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        handler.removeCallbacks(seekbarUpdater)
        super.onDestroy()
    }
}
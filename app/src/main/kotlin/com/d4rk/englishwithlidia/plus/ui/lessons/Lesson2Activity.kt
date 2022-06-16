package com.d4rk.englishwithlidia.plus.ui.lessons
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.databinding.ActivityLesson2Binding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.ceil
import kotlin.math.roundToInt
class Lesson2Activity: AppCompatActivity(), Runnable  {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private lateinit var seekBar: SeekBar
    private var wasPlaying = false
    private lateinit var fab: FloatingActionButton
    private lateinit var binding: ActivityLesson2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLesson2Binding.inflate(layoutInflater)
        setContentView(R.layout.activity_lesson2)
        fab = findViewById(R.id.lesson2PlayButton)
        fab.setOnClickListener { playSong() }
        val seekBarHint = findViewById<TextView>(R.id.lesson2LengthText)
        seekBar = findViewById(R.id.lesson2Seekbar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                seekBarHint.visibility = View.INVISIBLE
            }
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromTouch: Boolean) {
                seekBarHint.visibility = View.INVISIBLE
                val x = ceil((progress / 1000f).toDouble()).toInt()
                val i = "0:$x"
                if (x < 10) seekBarHint.text = i else seekBarHint.text = i
                val percent = progress / seekBar.max.toDouble()
                val offset = seekBar.thumbOffset
                val seekWidth = seekBar.width
                val `val` = (percent * (seekWidth - 2 * offset)).roundToInt()
                val labelWidth = seekBarHint.width
                seekBarHint.x = (offset + seekBar.x + `val` - (percent * offset).roundToInt() - (percent * labelWidth / 2).roundToInt())
                if (progress > 0 && !mediaPlayer.isPlaying) {
                    clearMediaPlayer()
                    fab.setImageDrawable(ContextCompat.getDrawable(this@Lesson2Activity, R.drawable.ic_media_play))
                    this@Lesson2Activity.seekBar.progress = 0
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
                seekBar.progress = 0
                wasPlaying = true
                fab.setImageDrawable(ContextCompat.getDrawable(this@Lesson2Activity, R.drawable.ic_play))
            }
            if (! wasPlaying) {
                fab.setImageDrawable(ContextCompat.getDrawable(this@Lesson2Activity, R.drawable.ic_pause))
                val descriptor = assets.openFd("lesson2.ogg")
                mediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                descriptor.close()
                mediaPlayer.prepare()
                mediaPlayer.setVolume(0.5f, 0.5f)
                mediaPlayer.isLooping = false
                seekBar.max = mediaPlayer.duration
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
            seekBar.progress = currentPosition
        }
    }
    override fun onDestroy() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            clearMediaPlayer()
        }
        super.onDestroy()
    }
    private fun clearMediaPlayer() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}
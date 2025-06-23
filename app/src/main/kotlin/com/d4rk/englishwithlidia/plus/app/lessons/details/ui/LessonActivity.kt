package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.d4rk.android.libs.apptoolkit.app.theme.style.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonActivity : AppCompatActivity() {
    private val viewModel: LessonViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val lessonId = intent?.data?.lastPathSegment
            lessonId?.let { lesson ->
                viewModel.getLesson(lessonId = lesson)
            }

            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LessonScreen(
                        activity = this@LessonActivity,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}

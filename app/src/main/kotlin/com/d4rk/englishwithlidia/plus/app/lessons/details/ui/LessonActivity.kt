package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.d4rk.englishwithlidia.plus.ui.screens.settings.display.theme.style.AppTheme

class LessonActivity : AppCompatActivity() {
    private val viewModel : LessonViewModel by viewModels()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val lessonId = intent?.data?.lastPathSegment
            println("English with Lidia Plus -> lesson id got is: $lessonId")
            lessonId?.let { lesson ->
                viewModel.getLesson(lessonId = lesson)
            }

            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize() , color = MaterialTheme.colorScheme.background
                ) {
                    val lesson = viewModel.uiState.collectAsState()
                    LessonScreen(
                        activity = this@LessonActivity ,
                        viewModel = viewModel ,
                        lesson = lesson.value ,
                    )
                }
            }
        }
    }
}
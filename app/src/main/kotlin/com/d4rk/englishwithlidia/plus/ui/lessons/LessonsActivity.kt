package com.d4rk.englishwithlidia.plus.ui.lessons

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.d4rk.englishwithlidia.plus.data.model.ui.lessons.UiLessonsAsset
import com.d4rk.englishwithlidia.plus.ui.settings.display.theme.style.AppTheme

class LessonsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val lessonDetails: UiLessonsAsset? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra("lessonDetails", UiLessonsAsset::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        intent.getParcelableExtra("lessonDetails")
                    }
                val viewModel: LessonsViewModel = viewModel(
                    factory = LessonsViewModelFactory(application, lessonDetails)
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LessonsComposable(viewModel)
                }
            }
        }
    }
}
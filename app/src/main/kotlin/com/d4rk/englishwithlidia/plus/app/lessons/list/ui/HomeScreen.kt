package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.LoadingScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components.LessonListLayout

@Composable
fun HomeScreen(
    context : Context ,
    viewModel : HomeViewModel ,
) {

    val uiState by viewModel.uiState.collectAsState()
    val visibilityStates by viewModel.visibilityStates.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()



    if (isLoading) {
        LoadingScreen()
    }
    else {
        LessonListLayout(
            lessons = uiState.lessons , context = context , visibilityStates = visibilityStates
        )
    }
}
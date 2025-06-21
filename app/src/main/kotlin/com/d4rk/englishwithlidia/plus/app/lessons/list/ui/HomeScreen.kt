package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.LoadingScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components.LessonListLayout
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen

@Composable
fun HomeScreen(
    viewModel : HomeViewModel ,
) {
    val screenState : UiStateScreen<UiHomeScreen> by viewModel.uiState.collectAsState()
    val uiState : UiHomeScreen = screenState.data ?: UiHomeScreen()
    val visibilityStates by viewModel.visibilityStates.collectAsState()

    if (screenState.screenState is ScreenState.IsLoading) {
        LoadingScreen()
    } else {
        LessonListLayout(
            lessons = uiState.lessons ,visibilityStates = visibilityStates
        )
    }
}
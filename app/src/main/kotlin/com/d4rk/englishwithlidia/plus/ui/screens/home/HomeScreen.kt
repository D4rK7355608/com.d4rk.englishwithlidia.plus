package com.d4rk.englishwithlidia.plus.ui.screens.home

import android.content.Context
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.d4rk.englishwithlidia.plus.data.model.ui.error.UiErrorModel
import com.d4rk.englishwithlidia.plus.ui.components.dialogs.ErrorAlertDialog
import com.d4rk.englishwithlidia.plus.ui.components.layouts.LessonListLayout
import com.d4rk.englishwithlidia.plus.ui.components.layouts.LoadingScreen

@Composable
fun HomeScreen(
    context : Context ,
    viewModel : HomeViewModel ,
) {
    val uiErrorModel : UiErrorModel by viewModel.uiErrorModel.collectAsState()

    val uiState by viewModel.uiState.collectAsState()
    val visibilityStates by viewModel.visibilityStates.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val transition : Transition<Boolean> =
            updateTransition(targetState = ! isLoading , label = "LoadingTransition")
    val progressAlpha : Float by transition.animateFloat(label = "Progress Alpha") {
        if (it) 0f else 1f
    }

    if (uiErrorModel.showErrorDialog) {
        ErrorAlertDialog(errorMessage = uiErrorModel.errorMessage ,
                         onDismiss = { viewModel.dismissErrorDialog() })
    }

    if (isLoading) {
        LoadingScreen(progressAlpha)
    }
    else {
        LessonListLayout(
            lessons = uiState.lessons , context = context , visibilityStates = visibilityStates
        )
    }
}
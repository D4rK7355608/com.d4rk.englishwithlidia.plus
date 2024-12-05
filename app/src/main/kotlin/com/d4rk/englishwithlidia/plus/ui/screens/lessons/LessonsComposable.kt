package com.d4rk.englishwithlidia.plus.ui.screens.lessons

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.data.model.ui.error.UiErrorModel
import com.d4rk.englishwithlidia.plus.ui.components.dialogs.ErrorAlertDialog
import com.d4rk.englishwithlidia.plus.ui.components.layouts.LessonListLayout
import com.d4rk.englishwithlidia.plus.ui.components.layouts.LoadingScreen
import com.d4rk.englishwithlidia.plus.ui.components.navigation.TopAppBarScaffoldWithBackButton

@Composable
fun LessonsComposable(viewModel : LessonsViewModel , activity : LessonsActivity) {
    val context = LocalContext.current
    val uiErrorModel : UiErrorModel by viewModel.uiErrorModel.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val visibilityStates by viewModel.visibilityStates.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val transition : Transition<Boolean> =
            updateTransition(targetState = ! isLoading , label = "LoadingTransition")
    val progressAlpha : Float by transition.animateFloat(label = "Progress Alpha") {
        if (it) 0f else 1f
    }

    TopAppBarScaffoldWithBackButton(title = stringResource(id = R.string.security_and_privacy) ,
                                    onBackClicked = { activity.finish() }) { paddingValues ->
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
}
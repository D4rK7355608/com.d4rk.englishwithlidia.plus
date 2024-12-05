package com.d4rk.englishwithlidia.plus.ui.screens.home

import android.content.Context
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.data.model.ui.error.UiErrorModel
import com.d4rk.englishwithlidia.plus.ui.components.animations.bounceClick
import com.d4rk.englishwithlidia.plus.ui.components.buttons.OutlinedUrlButtons
import com.d4rk.englishwithlidia.plus.ui.components.dialogs.ErrorAlertDialog
import com.d4rk.englishwithlidia.plus.ui.components.drawable.homeBanner
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
            lessons = uiState.lessons,
            context = context,
            visibilityStates = visibilityStates
        )
    }
}
/*

@Composable
fun HomeScreen(
    context : Context ,
    viewModel : HomeViewModel ,
) {
    val uiState by viewModel.uiState.collectAsState()
    val visibilityStates by viewModel.visibilityStates.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Image(
                imageVector = homeBanner() ,
                contentDescription = null ,
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 24.dp , end = 24.dp) ,
            ) {
                OutlinedUrlButtons(
                    vectorIcon = Icons.Outlined.Language ,
                    context = context ,
                    modifier = Modifier
                            .weight(1f)
                            .bounceClick() ,
                    text = R.string.website ,
                    url = "https://sites.google.com/view/englishwithlidia"
                )

                Spacer(modifier = Modifier.width(24.dp))

                OutlinedUrlButtons(
                    painterIcon = painterResource(id = R.drawable.ic_find_us) ,
                    context = context ,
                    modifier = Modifier
                            .weight(1f)
                            .bounceClick() ,
                    text = R.string.website ,
                    url = "https://www.facebook.com/lidia.melinte"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(all = 16.dp) ,
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        else {
            item {
                LessonListLayout(
                    lessons = uiState.lessons,
                    context = context ,
                    visibilityStates = visibilityStates
                )
            }
        }
    }
}*/

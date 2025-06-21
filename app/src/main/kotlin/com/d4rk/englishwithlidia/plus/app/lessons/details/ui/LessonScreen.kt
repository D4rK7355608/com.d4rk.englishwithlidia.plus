package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.LoadingScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.navigation.LargeTopAppBarWithScaffold
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.components.LessonContentLayout
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.core.data.datastore.DataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    activity : LessonActivity ,
    viewModel : LessonViewModel ,
    lesson : UiLessonScreen
) {
    val context = LocalContext.current
    val dataStore = DataStore.getInstance(context)
    val scrollState = rememberScrollState()
    val isLoading by viewModel.isLoading.collectAsState()

    LargeTopAppBarWithScaffold (
        title = lesson.lessonTitle ,
        onBackClicked = {

        }) { paddingValues ->


        if (isLoading) {
            LoadingScreen()
        }
        else {
            LessonContentLayout(
                paddingValues = paddingValues ,
                scrollState = scrollState ,
                dataStore = dataStore ,
                lesson = lesson ,
                viewModel = viewModel
            )
        }
    }
}
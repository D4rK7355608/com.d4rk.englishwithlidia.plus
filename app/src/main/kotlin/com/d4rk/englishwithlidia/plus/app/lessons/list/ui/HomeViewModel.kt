package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.getData
import com.d4rk.android.libs.apptoolkit.core.ui.base.ScreenViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeAction
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeEvent
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getHomeLessonsUseCase: GetHomeLessonsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : ScreenViewModel<UiHomeScreen, HomeEvent, HomeAction>(
    initialState = UiStateScreen(screenState = ScreenState.IsLoading(), data = UiHomeScreen())
) {

    private val _visibilityStates = MutableStateFlow<List<Boolean>>(emptyList())
    val visibilityStates: StateFlow<List<Boolean>> = _visibilityStates.asStateFlow()

    init {
        onEvent(HomeEvent.FetchLessons)
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.FetchLessons -> getHomeLessons()
        }
    }

    private fun getHomeLessons() {
        launch(context = dispatcherProvider.io) {
            val lessons = getHomeLessonsUseCase()
            withContext(dispatcherProvider.main) {
                if (lessons.lessons.isEmpty()) {
                    screenState.update { current ->
                        current.copy(screenState = ScreenState.NoData(), data = lessons)
                    }
                } else {
                    screenState.update { current ->
                        current.copy(screenState = ScreenState.Success(), data = lessons)
                    }
                }
                initializeVisibilityStates()
            }
        }
    }

    private fun initializeVisibilityStates() {
        launch(context = dispatcherProvider.default) {
            delay(timeMillis = 50L)
            val lessonCount = screenState.getData().lessons.size
            _visibilityStates.value = List(lessonCount) { false }
            screenState.getData().lessons.indices.forEach { index ->
                delay(timeMillis = index * 8L)
                _visibilityStates.value = List(lessonCount) { lessonIndex ->
                    lessonIndex == index || _visibilityStates.value[lessonIndex]
                }
            }
        }
    }
}

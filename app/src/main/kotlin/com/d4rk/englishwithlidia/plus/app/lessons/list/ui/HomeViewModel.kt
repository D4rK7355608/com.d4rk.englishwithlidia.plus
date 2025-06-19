package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import com.d4rk.englishwithlidia.plus.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    application: Application,
    private val getHomeLessonsUseCase: GetHomeLessonsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : BaseViewModel(application) {

    private val _uiState = MutableStateFlow(UiHomeScreen())
    val uiState: StateFlow<UiHomeScreen> = _uiState.asStateFlow()

    init {
        getHomeLessons()
    }

    private fun getHomeLessons() {
        viewModelScope.launch(coroutineExceptionHandler + dispatcherProvider.io) {
            showLoading()
            val lessons = getHomeLessonsUseCase()
            withContext(dispatcherProvider.main) {
                _uiState.value = lessons
                hideLoading()
                initializeVisibilityStates()
            }
        }
    }

    private fun initializeVisibilityStates() {
        viewModelScope.launch(coroutineExceptionHandler) {
            delay(timeMillis = 50L)
            val lessonCount = _uiState.value.lessons.size
            _visibilityStates.value = List(lessonCount) { false }
            _uiState.value.lessons.indices.forEach { index ->
                delay(timeMillis = index * 8L)
                _visibilityStates.value = List(lessonCount) { lessonIndex ->
                    lessonIndex == index || _visibilityStates.value[lessonIndex]
                }
            }
        }
    }
}

package com.d4rk.englishwithlidia.plus.ui.screens.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.home.UiHomeScreen
import com.d4rk.englishwithlidia.plus.ui.screens.home.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application : Application) : BaseViewModel(application) {
    private val repository = LessonRepository(DataStore(application) , application)

    private val _uiState = MutableStateFlow(UiHomeScreen())
    val uiState: StateFlow<UiHomeScreen> = _uiState

    init {
        getHomeLessons()
    }

    private fun getHomeLessons() {
        viewModelScope.launch(coroutineExceptionHandler) {
            showLoading()
            repository.getHomeLessonsRepository { fetchedLessons ->
                _uiState.value = fetchedLessons
            }
            hideLoading()
            initializeVisibilityStates()
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
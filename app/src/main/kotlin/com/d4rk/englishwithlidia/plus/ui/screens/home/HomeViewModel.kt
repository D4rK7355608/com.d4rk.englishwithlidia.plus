package com.d4rk.englishwithlidia.plus.ui.screens.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.UiLessonsAsset
import com.d4rk.englishwithlidia.plus.ui.screens.home.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application : Application) : BaseViewModel(application) {
    private val repository = LessonRepository(DataStore(application) , application)

    private val _lessons = MutableStateFlow<List<UiLessonsAsset>>(emptyList())
    val lessons : StateFlow<List<UiLessonsAsset>> = _lessons

    init {
        getHomeLessons()
    }

    private fun getHomeLessons() {
        viewModelScope.launch(coroutineExceptionHandler) {
            showLoading()
            repository.getHomeLessonsRepository { fetchedLessons ->
                _lessons.value = fetchedLessons
            }
            hideLoading()
            initializeVisibilityStates()
        }
    }

    private fun initializeVisibilityStates() {
        viewModelScope.launch(coroutineExceptionHandler) {
            delay(timeMillis = 50L)
            _visibilityStates.value = List(_lessons.value.size) { false }
            _lessons.value.indices.forEach { index ->
                delay(timeMillis = index * 8L)
                _visibilityStates.value = List(_visibilityStates.value.size) { lessonIndex ->
                    lessonIndex == index || _visibilityStates.value[lessonIndex]
                }
            }
        }
    }
}
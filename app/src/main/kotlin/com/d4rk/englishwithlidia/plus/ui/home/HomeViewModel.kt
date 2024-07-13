package com.d4rk.englishwithlidia.plus.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d4rk.englishwithlidia.plus.data.model.ui.lessons.UiLessonsAsset
import com.d4rk.englishwithlidia.plus.ui.home.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: LessonRepository) : ViewModel() {
    private val _lessons = MutableStateFlow<List<UiLessonsAsset>>(emptyList())
    val lessons: StateFlow<List<UiLessonsAsset>> = _lessons

    init {
        viewModelScope.launch {
            _lessons.value = repository.getLessonData()
        }
    }
}
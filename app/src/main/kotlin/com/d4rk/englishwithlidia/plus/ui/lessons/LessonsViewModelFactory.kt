package com.d4rk.englishwithlidia.plus.ui.lessons

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.d4rk.englishwithlidia.plus.data.model.ui.lessons.UiLessonsAsset

class LessonsViewModelFactory(
    private val application: Application,
    private val lessonDetails: UiLessonsAsset?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LessonsViewModel(application, lessonDetails) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
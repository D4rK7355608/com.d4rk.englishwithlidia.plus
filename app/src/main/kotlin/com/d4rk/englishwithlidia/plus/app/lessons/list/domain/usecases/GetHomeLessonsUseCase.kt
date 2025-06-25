package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository

class GetHomeLessonsUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(): UiHomeScreen {
        return repository.getHomeLessons()
    }
}

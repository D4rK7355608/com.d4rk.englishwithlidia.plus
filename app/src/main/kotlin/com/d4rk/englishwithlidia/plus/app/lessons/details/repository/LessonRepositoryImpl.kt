package com.d4rk.englishwithlidia.plus.app.lessons.details.repository

import android.app.Application
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiLessonScreen

class LessonRepositoryImpl(
) : LessonRepository, LessonRepositoryImplementation() {

    override suspend fun getLesson(lessonId: String): UiLessonScreen {
        return getLessonImplementation(lessonId = lessonId)
    }
}

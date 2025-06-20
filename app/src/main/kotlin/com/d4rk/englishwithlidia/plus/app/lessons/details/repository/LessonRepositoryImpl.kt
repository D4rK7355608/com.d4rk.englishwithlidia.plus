package com.d4rk.englishwithlidia.plus.app.lessons.details.repository

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiLessonScreen
import io.ktor.client.HttpClient

class LessonRepositoryImpl(
    client: HttpClient,
) : LessonRepository, LessonRepositoryImplementation(client) {

    override suspend fun getLesson(lessonId: String): UiLessonScreen {
        return invoke(lessonId = lessonId)
    }
}

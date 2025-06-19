package com.d4rk.englishwithlidia.plus.app.lessons.details.repository

import android.app.Application
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiLessonScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LessonRepository(
   application : Application ,
) : LessonRepositoryImplementation(application) {

    suspend fun getLessonRepository(
        lessonId : String , onSuccess : (UiLessonScreen) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val lesson = getLessonImplementation(lessonId = lessonId)
            withContext(Dispatchers.Main) {
                onSuccess(lesson)
            }
        }
    }
}
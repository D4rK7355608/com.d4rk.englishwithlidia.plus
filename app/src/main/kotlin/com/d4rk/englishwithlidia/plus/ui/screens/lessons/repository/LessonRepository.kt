package com.d4rk.englishwithlidia.plus.ui.screens.lessons.repository

import android.app.Application
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.home.UiLessonScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LessonRepository(
    dataStore : DataStore , application : Application ,
) : LessonRepositoryImplementation(application , dataStore) {

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
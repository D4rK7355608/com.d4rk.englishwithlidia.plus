package com.d4rk.englishwithlidia.plus.ui.screens.home.repository

import android.app.Application
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.UiLessonsAsset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LessonRepository(
    dataStore : DataStore , application : Application ,
) : LessonRepositoryImplementation(application , dataStore) {

    suspend fun getHomeLessonsRepository(onSuccess : (List<UiLessonsAsset>) -> Unit) {
        withContext(Dispatchers.IO) {
            val lessons = getHomeLessonsImplementation()
            withContext(Dispatchers.Main) {
                onSuccess(lessons)
            }
        }
    }
}
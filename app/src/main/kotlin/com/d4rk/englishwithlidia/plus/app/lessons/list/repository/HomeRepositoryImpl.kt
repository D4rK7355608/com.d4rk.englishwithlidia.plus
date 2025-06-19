package com.d4rk.englishwithlidia.plus.app.lessons.list.repository

import android.app.Application
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore

class HomeRepositoryImpl(
    dataStore: DataStore,
    application: Application,
) : HomeRepository, HomeRepositoryImplementation(application, dataStore) {

    override suspend fun getHomeLessons(): UiHomeScreen {
        return getHomeLessonsImplementation()
    }
}

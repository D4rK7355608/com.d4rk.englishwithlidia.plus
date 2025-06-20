package com.d4rk.englishwithlidia.plus.app.lessons.list.repository

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import io.ktor.client.HttpClient

class HomeRepositoryImpl(
    client: HttpClient,
) : HomeRepository, HomeRepositoryImplementation(client) {

    override suspend fun getHomeLessons(): UiHomeScreen {
        return getHomeLessonsImplementation()
    }
}

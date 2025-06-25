package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen

interface HomeRepository {
    suspend fun getHomeLessons(): UiHomeScreen
}

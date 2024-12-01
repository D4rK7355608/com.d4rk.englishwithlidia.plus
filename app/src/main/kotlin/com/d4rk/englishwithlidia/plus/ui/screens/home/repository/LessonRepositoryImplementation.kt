package com.d4rk.englishwithlidia.plus.ui.screens.home.repository

import android.app.Application
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.constants.api.ApiConstants
import com.d4rk.englishwithlidia.plus.data.core.AppCoreManager
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.data.model.api.ApiResponse
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.UiLessonsAsset
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

abstract class LessonRepositoryImplementation(
    val application : Application ,
    val dataStore : DataStore ,
) {
    private val client : HttpClient = AppCoreManager.ktorClient

    private val baseUrl = BuildConfig.DEBUG.let { isDebug ->
        val environment = if (isDebug) "debug" else "release"
        "${ApiConstants.BASE_REPOSITORY_URL}/$environment/ro/home/api_get_lessons.json"
    }

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    suspend fun getHomeLessonsImplementation() : List<UiLessonsAsset> {
        return runCatching {
            val response = client.get(baseUrl)
            val jsonString = response.bodyAsText()

            jsonString.takeUnless { it.isBlank() }
                    ?.let { jsonParser.decodeFromString<ApiResponse>(it) }
                    ?.takeIf { it.data.isNotEmpty() }?.data?.mapNotNull { networkLesson ->
                        runCatching {
                            UiLessonsAsset(
                                id = networkLesson.id ,
                                title = networkLesson.title ,
                                banner = networkLesson.banner ,
                                lessonDetails = UiLessonsAsset.UiLessonDetails(
                                    title = networkLesson.lessonDetails.titleExtension ,
                                    audioUrl = networkLesson.lessonDetails.podcast ,
                                    lessonIntro = networkLesson.lessonDetails.intro ,
                                    lessonSummary = networkLesson.lessonDetails.summary
                                )
                            )
                        }.getOrNull()
                    }?.also { lessons ->
                        println("English with Lidia Plus -> Fetched ${lessons.size} lessons")
                    } ?: emptyList()
        }.getOrElse { exception ->
            println("English with Lidia Plus -> Error: ${exception.message}")
            emptyList()
        }
    }
}
package com.d4rk.englishwithlidia.plus.app.lessons.details.repository

import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonResponse
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.ApiConstants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

open class LessonRepositoryImplementation(private val client: HttpClient) {

    private val baseUrl = BuildConfig.DEBUG.let { isDebug ->
        val environment = if (isDebug) "debug" else "release"
        "${ApiConstants.BASE_REPOSITORY_URL}/$environment/ro/lessons"
    }

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    suspend operator fun invoke(lessonId: String): UiLessonScreen {
        return runCatching {
            val url = "$baseUrl/api_get_$lessonId.json"
            val jsonString = client.get(url).bodyAsText()

            val lessons = jsonString.takeUnless { it.isBlank() }
                ?.let { jsonParser.decodeFromString<ApiLessonResponse>(it) }
                ?.takeIf { it.data.isNotEmpty() }?.data?.map { networkLesson ->
                    UiLessonScreen(
                        lessonTitle = networkLesson.lessonTitle,
                        lessonContent = ArrayList(
                            networkLesson.lessonContent.map { networkContent ->
                                UiLessonContent(
                                    contentId = networkContent.contentId,
                                    contentType = networkContent.contentType,
                                    contentText = networkContent.contentText,
                                    contentAudioUrl = networkContent.contentAudioUrl,
                                    contentImageUrl = networkContent.contentImageUrl,
                                )
                            },
                        ),
                    )
                } ?: emptyList()
            lessons.firstOrNull() ?: UiLessonScreen()
        }.getOrElse {
            UiLessonScreen()
        }
    }
}
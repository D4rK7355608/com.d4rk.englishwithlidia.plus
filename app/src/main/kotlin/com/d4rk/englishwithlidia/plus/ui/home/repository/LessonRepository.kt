package com.d4rk.englishwithlidia.plus.ui.home.repository

import com.d4rk.englishwithlidia.plus.data.model.api.ApiResponse
import com.d4rk.englishwithlidia.plus.data.model.ui.lessons.UiLessonDetails
import com.d4rk.englishwithlidia.plus.data.model.ui.lessons.UiLessonsAsset
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

class LessonRepository {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getLessonData(): List<UiLessonsAsset> {
        val url =
            "https://script.googleusercontent.com/macros/echo?user_content_key=IqfXaaMa5_xVh9PXgCkTSSvJ5oXI2E6ovHb-xZ-9oeDNzYVvgbAQub1KyRTJ8iBLSdRQS9gDeA6vYWII3idMI7fMbNijCwsvm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnJ73NK6pJWSpPRfbuzxijC-yIhItdrlA4B3P1J--f1LjXLtmxSn7AD8ey3pHscewS7Oo7Ec-OFT-mp7LnhIc8L9DWUmcQiBQTNz9Jw9Md8uu&lib=MSUzLTgsn65WMd0y5_jDbxNmCwTyR-I_G"
        return try {
            val response: ApiResponse = client.get(url).body()
            response.data.map { networkLesson ->
                UiLessonsAsset(
                    id = networkLesson.id,
                    title = networkLesson.title,
                    banner = networkLesson.banner,
                    lessonDetails = UiLessonDetails(
                        title = networkLesson.lessonDetailsTitle,
                        audioUrl = networkLesson.lessonDetailsAudioUrl,
                        lessonIntro = networkLesson.lessonDetailsLessonIntro,
                        lessonSummary = networkLesson.lessonDetailsLessonSummary
                    )
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
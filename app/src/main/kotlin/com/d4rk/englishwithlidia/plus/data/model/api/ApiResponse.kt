package com.d4rk.englishwithlidia.plus.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    @SerialName("data") val data: List<NetworkLesson>
)

@Serializable
data class NetworkLesson(
    @SerialName("lesson_id") val id: Int,
    @SerialName("lesson_title") val title: String,
    @SerialName("lesson_banner") val banner: String,
    @SerialName("lesson_details") val lessonDetails: LessonDetails
)

@Serializable
data class LessonDetails(
    @SerialName("lesson_details_title_extension") val titleExtension: String,
    @SerialName("lesson_details_podcast") val podcast: String,
    @SerialName("lesson_details_intro") val intro: String,
    @SerialName("lesson_details_summary") val summary: String
)
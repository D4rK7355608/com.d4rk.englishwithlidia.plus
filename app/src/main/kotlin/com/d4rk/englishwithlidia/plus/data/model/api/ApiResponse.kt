package com.d4rk.englishwithlidia.plus.data.model.api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ApiResponse(
    val data: List<NetworkLesson>
)

@Serializable
data class NetworkLesson @OptIn(ExperimentalSerializationApi::class) constructor(
    val id: Int,
    val title: String,
    val banner: String,
    @JsonNames("lessonDetails.title") val lessonDetailsTitle: String,
    @JsonNames("lessonDetails.audioUrl") val lessonDetailsAudioUrl: String,
    @JsonNames("lessonDetails.lessonIntro") val lessonDetailsLessonIntro: String,
    @JsonNames("lessonDetails.lessonSummary") val lessonDetailsLessonSummary: String
)
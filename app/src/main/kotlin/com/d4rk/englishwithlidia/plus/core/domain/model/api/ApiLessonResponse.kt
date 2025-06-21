package com.d4rk.englishwithlidia.plus.core.domain.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiLessonResponse(
    @SerialName("data") val data : List<ApiLesson> = ArrayList()
)

@Serializable
data class ApiLesson(
    @SerialName("lesson_title") val lessonTitle : String = "" ,
    @SerialName("lesson_content") val lessonContent : List<ApiLessonContent> = emptyList()
)

@Serializable
data class ApiLessonContent(
    @SerialName("content_id") val contentId : String = "" ,
    @SerialName("content_type") val contentType : String = "" ,
    @SerialName("content_text") val contentText : String = "" ,
    @SerialName("content_audio_url") val contentAudioUrl : String = "" ,
    @SerialName("content_image_url") val contentImageUrl : String = "" ,
    @SerialName("content_thumbnail_url") val contentThumbnailUrl : String = ""
)
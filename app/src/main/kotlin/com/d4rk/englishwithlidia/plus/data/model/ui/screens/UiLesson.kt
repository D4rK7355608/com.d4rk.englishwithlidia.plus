package com.d4rk.englishwithlidia.plus.data.model.ui.screens

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UiLesson(
    @SerialName("lesson_id") val lessonId: String = "" ,
    @SerialName("lesson_title") val lessonTitle: String = "" ,
    @SerialName("lesson_type") val lessonType: String = "",
    @SerialName("thumbnail_image_url") var thumbnailImageUrl : String = "" ,
    @SerialName("deep_link_path") val deepLinkPath: String = "" ,
)
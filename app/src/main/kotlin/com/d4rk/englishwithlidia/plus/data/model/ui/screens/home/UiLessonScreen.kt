package com.d4rk.englishwithlidia.plus.data.model.ui.screens.home

data class UiLessonScreen(
    val lessonTitle : String = "" ,
    val lessonContent : List<UiLessonContent> = emptyList()
)

data class UiLessonContent(
    val contentId : String = "" ,
    val contentType : String = "" ,
    val contentText : String = "" ,
    val contentImageUrl : String = "" ,
    val contentAudioUrl : String = "" ,
)
package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui

data class UiLessonScreen(
    val isPlaying : Boolean = false ,
    val playbackPosition : Long = 0L ,
    val playbackDuration : Long = 0L ,
    val lessonTitle : String = "" ,
    val lessonContent : ArrayList<UiLessonContent> = ArrayList()
)

data class UiLessonContent(
    val contentId : String = "" ,
    val contentType : String = "" ,
    val contentText : String = "" ,
    val contentImageUrl : String = "" ,
    val contentAudioUrl : String = "" ,
    val contentThumbnailUrl : String = "" ,
)
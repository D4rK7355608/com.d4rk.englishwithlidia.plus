package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui

data class UiHomeScreen(
    val lessons : ArrayList<UiHomeLesson> = ArrayList()
)

data class UiHomeLesson(
    val lessonId : String = "" ,
    val lessonTitle : String = "" ,
    val lessonType : String = "" ,
    var lessonThumbnailImageUrl : String = "" ,
    val lessonDeepLinkPath : String = "" ,
)
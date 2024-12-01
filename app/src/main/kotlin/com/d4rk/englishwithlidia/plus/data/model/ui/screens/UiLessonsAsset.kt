package com.d4rk.englishwithlidia.plus.data.model.ui.screens

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiLessonsAsset(
    val id : Int , val title : String , val banner : String , val lessonDetails : UiLessonDetails
) : Parcelable {
    @Parcelize
    data class UiLessonDetails(
        val title : String ,
        val audioUrl : String ,
        val lessonIntro : String ,
        val lessonSummary : String
    ) : Parcelable
}
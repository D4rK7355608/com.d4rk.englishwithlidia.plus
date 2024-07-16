package com.d4rk.englishwithlidia.plus.data.model.ui.lessons

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiLessonDetails(
    val title: String,
    val audioUrl: String,
    val lessonIntro: String,
    val lessonSummary: String
) : Parcelable
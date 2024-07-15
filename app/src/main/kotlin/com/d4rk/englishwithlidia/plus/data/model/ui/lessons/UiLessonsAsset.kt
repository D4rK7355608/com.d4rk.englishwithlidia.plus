package com.d4rk.englishwithlidia.plus.data.model.ui.lessons

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiLessonsAsset(
    val id: Int,
    val title: String,
    val banner: String,
    val lessonDetails: UiLessonDetails
) : Parcelable
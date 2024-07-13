package com.d4rk.englishwithlidia.plus.data.model.ui.lessons

import android.os.Parcelable
import androidx.annotation.RawRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiLessonDetails(
    val title: String,
    @RawRes val audioResId: Int,
    val lessonIntro: String,
    val lessonSummary: String
) : Parcelable
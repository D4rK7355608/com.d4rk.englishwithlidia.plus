package com.d4rk.englishwithlidia.plus.ui.home.repository

import android.content.Context
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.data.model.ui.UiLessonAsset

class LessonRepository(private val context: Context) {

    fun getLessonData(): List<UiLessonAsset> {
        return listOf(
            UiLessonAsset(
                title = context.getString(R.string.lesson_1_title),
                imageResource = R.drawable.im_lesson1
            ),
            UiLessonAsset(
                title = context.getString(R.string.lesson_2_title),
                imageResource = R.drawable.im_lesson2
            ),
            // ... more lesson data
        )
    }
}
package com.d4rk.englishwithlidia.plus.ui.home.repository

import android.content.Context
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.data.model.ui.lessons.UiLessonDetails
import com.d4rk.englishwithlidia.plus.data.model.ui.lessons.UiLessonsAsset

class LessonRepository(private val context: Context) {

    fun getLessonData(): List<UiLessonsAsset> {
        return listOf(
            UiLessonsAsset(
                id = 1,
                title = context.getString(R.string.lesson_1_title),
                banner = R.drawable.im_lesson1,
                lessonDetails = UiLessonDetails(
                    title = context.getString(R.string.lesson_1_title),
                    audioResId = R.raw.lesson1,
                    lessonIntro = context.getString(R.string.introduction_first_lesson),
                    lessonSummary = context.getString(R.string.summary_first_lesson)
                )
            ),
            UiLessonsAsset(
                id = 2,
                title = context.getString(R.string.lesson_2_title),
                banner = R.drawable.im_lesson2,
                lessonDetails = UiLessonDetails(
                    title = context.getString(R.string.lesson_2_title),
                    audioResId = R.raw.lesson2,
                    lessonIntro = context.getString(R.string.introduction_second_lesson),
                    lessonSummary = context.getString(R.string.summary_second_lesson)
                )
            ),
        )
    }
}
package com.d4rk.englishwithlidia.plus.ui.components.navigation

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeLesson

fun openLessonDetailActivity(context : Context , lesson : UiHomeLesson) {
    println("English with Lidia -> openLessonDetailActivity")

    println("English with Lidia -> openLessonDetailActivity checking deep link path -> ${lesson.lessonDeepLinkPath}")

    Intent(Intent.ACTION_VIEW , lesson.lessonDeepLinkPath.toUri()).let { intent ->
        println("English with Lidia -> openLessonDetailActivity intent -> $intent")
        intent.resolveActivity(context.packageManager)?.let {
            println("English with Lidia -> openLessonDetailActivity start activity -> $it")
            context.startActivity(intent)
        }
    }
}
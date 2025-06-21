package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.action

import com.d4rk.android.libs.apptoolkit.core.ui.base.handling.UiEvent

sealed interface LessonEvent : UiEvent {
    data class FetchLesson(val lessonId: String) : LessonEvent
}

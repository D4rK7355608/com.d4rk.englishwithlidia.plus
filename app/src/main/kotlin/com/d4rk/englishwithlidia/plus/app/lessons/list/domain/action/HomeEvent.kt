package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action

import com.d4rk.android.libs.apptoolkit.core.ui.base.handling.UiEvent

sealed interface HomeEvent : UiEvent {
    data object FetchLessons : HomeEvent
}

package com.d4rk.englishwithlidia.plus.data.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: Int, val selectedIcon: ImageVector, val badgeCount: Int? = null
)
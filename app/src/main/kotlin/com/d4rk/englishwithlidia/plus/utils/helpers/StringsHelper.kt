package com.d4rk.englishwithlidia.plus.utils.helpers

import androidx.annotation.StringRes
import com.d4rk.englishwithlidia.plus.data.core.AppCoreManager

fun getStringResource(@StringRes id : Int) : String {
    return AppCoreManager.instance.getString(id)
}
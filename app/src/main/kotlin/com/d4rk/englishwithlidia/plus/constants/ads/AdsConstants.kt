package com.d4rk.englishwithlidia.plus.constants.ads

import com.d4rk.englishwithlidia.plus.BuildConfig

object AdsConstants {

    val BANNER_AD_UNIT_ID: String
        get() = if (BuildConfig.DEBUG) {
            "ca-app-pub-3940256099942544/6300978111"
        } else {
            "ca-app-pub-5294151573817700/8479403125"
        }

    val APP_OPEN_UNIT_ID: String
        get() = if (BuildConfig.DEBUG) {
            "ca-app-pub-3940256099942544/9257395921"
        } else {
            "ca-app-pub-5294151573817700/2885662643"
        }
}
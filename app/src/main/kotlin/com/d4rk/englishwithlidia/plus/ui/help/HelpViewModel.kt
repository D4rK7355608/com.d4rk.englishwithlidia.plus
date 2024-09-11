package com.d4rk.englishwithlidia.plus.ui.help

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.d4rk.englishwithlidia.plus.utils.IntentUtils
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HelpViewModel(application: Application) : AndroidViewModel(application) {

    private var _reviewInfo: MutableState<ReviewInfo?> = mutableStateOf(null)
    val reviewInfo: State<ReviewInfo?> = _reviewInfo

    fun requestReviewFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            val reviewManager = ReviewManagerFactory.create(getApplication())
            val request = reviewManager.requestReviewFlow()
            val packageName = getApplication<Application>().packageName
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _reviewInfo.value = task.result
                } else {
                    task.exception?.let {
                        task.exception?.printStackTrace()
                        IntentUtils.sendEmailToDeveloper(getApplication())
                    }
                }
            }.addOnFailureListener {
                IntentUtils.openUrl(
                    getApplication(),
                    "https://play.google.com/store/apps/details?id=$packageName&showAllReviews=true"
                )
            }
        }
    }

    fun launchReviewFlow(activity: HelpActivity, reviewInfo: ReviewInfo) {
        val reviewManager = ReviewManagerFactory.create(activity)
        reviewManager.launchReviewFlow(activity, reviewInfo)
    }
}
package com.d4rk.englishwithlidia.plus.ui.screens.main

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.notifications.managers.AppUpdateNotificationsManager
import com.d4rk.englishwithlidia.plus.ui.screens.main.repository.MainRepository
import com.d4rk.englishwithlidia.plus.ui.screens.startup.StartupActivity
import com.d4rk.englishwithlidia.plus.ui.viewmodel.BaseViewModel
import com.d4rk.englishwithlidia.plus.utils.IntentUtils
import com.google.android.play.core.appupdate.AppUpdateManager
import kotlinx.coroutines.launch

class MainViewModel(application : Application) : BaseViewModel(application) {
    private val repository = MainRepository(DataStore(application) , application)

    fun checkForUpdates(activity : Activity , appUpdateManager : AppUpdateManager) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.checkForUpdates(
                appUpdateManager = appUpdateManager , activity = activity
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAndScheduleUpdateNotifications(appUpdateNotificationsManager : AppUpdateNotificationsManager) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.checkAndScheduleUpdateNotifications(appUpdateNotificationsManager)
        }
    }

    fun checkAppUsageNotifications() {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.checkAppUsageNotifications()
        }
    }

    fun checkAndHandleStartup() {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.checkAndHandleStartup { isFirstTime ->
                if (isFirstTime) {
                    IntentUtils.openActivity(getApplication() , StartupActivity::class.java)
                }
            }
        }
    }

    fun configureSettings() {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.setupSettings()
        }
    }
}
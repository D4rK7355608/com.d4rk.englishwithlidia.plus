package com.d4rk.englishwithlidia.plus.core.di.modules

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.d4rk.android.libs.apptoolkit.app.main.domain.usecases.PerformInAppUpdateUseCase
import com.d4rk.android.libs.apptoolkit.app.oboarding.utils.interfaces.providers.OnboardingProvider
import com.d4rk.android.libs.apptoolkit.data.client.KtorClient
import com.d4rk.android.libs.apptoolkit.data.core.ads.AdsCoreManager
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.LessonRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.LessonViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.HomeRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.HomeViewModel
import com.d4rk.englishwithlidia.plus.app.main.ui.MainViewModel
import com.d4rk.englishwithlidia.plus.app.onboarding.utils.interfaces.providers.AppOnboardingProvider
import com.d4rk.englishwithlidia.plus.core.data.datastore.DataStore
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val appModule : Module = module {
    single<DataStore> { DataStore.getInstance(context = get()) }
    single<AdsCoreManager> { AdsCoreManager(context = get() , get()) }
    single { KtorClient().createClient() }

    single<OnboardingProvider> { AppOnboardingProvider() }

    single<AppUpdateManager> { AppUpdateManagerFactory.create(get()) }
    factory { (launcher : ActivityResultLauncher<IntentSenderRequest>) ->
        PerformInAppUpdateUseCase(appUpdateManager = get() , updateResultLauncher = launcher)
    }

    viewModel { (launcher : ActivityResultLauncher<IntentSenderRequest>) ->
        MainViewModel(performInAppUpdateUseCase = get { parametersOf(launcher) } , dispatcherProvider = get())
    }

    // Lessons
    single<HomeRepository> { HomeRepositoryImpl(client = get()) }
    factory { GetHomeLessonsUseCase(repository = get()) }
    viewModel { HomeViewModel(getHomeLessonsUseCase = get(), dispatcherProvider = get()) }

    single<LessonRepository> { LessonRepositoryImpl(client = get()) }
    factory { GetLessonUseCase(repository = get()) }
    viewModel { LessonViewModel(getLessonUseCase = get(), dispatcherProvider = get()) }
}
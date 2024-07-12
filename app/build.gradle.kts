import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.devtools.ksp")
}
android {
    compileSdk = 34
    namespace = "com.d4rk.englishwithlidia.plus"
    defaultConfig {
        applicationId = "com.d4rk.englishwithlidia.plus"
        minSdk = 26
        targetSdk = 34
        versionCode = 54
        versionName = "5.0.0"
        archivesName = "${applicationId}-v${versionName}"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resourceConfigurations += listOf(
            "en",
            "de",
            "es",
            "fr",
            "hi",
            "hu",
            "in",
            "it",
            "ja",
            "ro",
            "ru",
            "tr",
            "sv",
            "bg",
            "pl",
            "uk"
        )
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            multiDexEnabled = true
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            versionNameSuffix = null
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        debug {
            multiDexEnabled = true
            isDebuggable = true
            versionNameSuffix = null
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    bundle {
        storeArchive {
            enable = true
        }
    }
}
dependencies {
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.perf)

    // Google
    implementation(libs.play.services.ads)
    implementation(libs.billing)
    implementation(libs.material)
    implementation(libs.play.services.oss.licenses)
    implementation(libs.review.ktx)
    implementation(libs.app.update.ktx)
    implementation(libs.volley)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.graphics.shapes)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.ui.tooling)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.navigation.compose)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.work.runtime.ktx)

    // KSP
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    // Kotlin
    implementation(libs.kotlinx.coroutines.android)

    implementation("com.airbnb.android:lottie-compose:4.0.0")

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
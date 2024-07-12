package com.d4rk.englishwithlidia.plus.data.datastore

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("settings")

class DataStore(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        @Volatile
        private var instance: DataStore? = null

        fun getInstance(context: Context): DataStore {
            return instance ?: synchronized(this) {
                instance ?: DataStore(context).also { instance = it }
            }
        }
    }

    // Last used app notifications
    private val lastUsedKey = longPreferencesKey("last_used")
    val lastUsed: Flow<Long> = dataStore.data.map { preferences ->
        preferences[lastUsedKey] ?: 0
    }

    suspend fun saveLastUsed(timestamp: Long) {
        dataStore.edit { preferences ->
            preferences[lastUsedKey] = timestamp
        }
    }


    // Startup
    private val startupKey = booleanPreferencesKey("value")
    val startup: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[startupKey] ?: true
    }

    suspend fun saveStartup(isFirstTime: Boolean) {
        dataStore.edit { preferences ->
            preferences[startupKey] = isFirstTime
        }
    }

    // Display
    val themeModeState = mutableStateOf("follow_system")
    private val themeModeKey = stringPreferencesKey("theme_mode")
    val themeMode: Flow<String> = dataStore.data.map { preferences ->
        preferences[themeModeKey] ?: "follow_system"
    }

    suspend fun saveThemeMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[themeModeKey] = mode
        }
    }

    private val amoledModeKey = booleanPreferencesKey("amoled_mode")
    val amoledMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[amoledModeKey] ?: false
    }

    suspend fun saveAmoledMode(isChecked: Boolean) {
        dataStore.edit { preferences ->
            preferences[amoledModeKey] = isChecked
        }
    }

    private val dynamicColorsKey = booleanPreferencesKey("dynamic_colors")
    val dynamicColors: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[dynamicColorsKey] ?: true
    }

    suspend fun saveDynamicColors(isChecked: Boolean) {
        dataStore.edit { preferences ->
            preferences[dynamicColorsKey] = isChecked
        }
    }

    private val languageKey = stringPreferencesKey("language")

    fun getLanguage(): Flow<String> = dataStore.data.map { preferences ->
        preferences[languageKey] ?: "en"
    }

    suspend fun saveLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[languageKey] = language
        }
    }

    private val currencyKey = stringPreferencesKey("preferred_currency")

    fun getCurrency(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[currencyKey] ?: ""
        }
    }

    suspend fun saveCurrency(currency: String) {
        dataStore.edit { preferences ->
            preferences[currencyKey] = currency
        }
    }

    // Usage and Diagnostics
    private val usageAndDiagnosticsKey = booleanPreferencesKey("usage_and_diagnostics")
    val usageAndDiagnostics: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[usageAndDiagnosticsKey] ?: true
    }

    suspend fun saveUsageAndDiagnostics(isChecked: Boolean) {
        dataStore.edit { preferences ->
            preferences[usageAndDiagnosticsKey] = isChecked
        }
    }

    // Ads
    private val adsKey = booleanPreferencesKey("ads")
    val ads: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[adsKey] ?: true
    }

    suspend fun saveAds(isChecked: Boolean) {
        dataStore.edit { preferences ->
            preferences[adsKey] = isChecked
        }
    }
}
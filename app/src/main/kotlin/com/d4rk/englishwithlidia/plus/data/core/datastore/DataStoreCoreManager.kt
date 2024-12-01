package com.d4rk.englishwithlidia.plus.data.core.datastore

import android.content.Context
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull

open class DataStoreCoreManager(protected val context: Context) {

    private var isDataStoreLoaded = false
    lateinit var dataStore: DataStore

    suspend fun initializeDataStore(): Boolean = coroutineScope {
        dataStore = DataStore.getInstance(context = context.applicationContext)

        listOf(
            async { dataStore.getLanguage().firstOrNull() ?: "en" },
        ).awaitAll()

        isDataStoreLoaded = true
        return@coroutineScope isDataStoreLoaded
    }
}
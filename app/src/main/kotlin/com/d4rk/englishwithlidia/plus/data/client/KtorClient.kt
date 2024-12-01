package com.d4rk.englishwithlidia.plus.data.client

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {

    private val networkTimeout = 6_000L

    fun createClient() : HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = networkTimeout
                connectTimeoutMillis = networkTimeout
                socketTimeoutMillis = networkTimeout
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message : String) {
                        println("English with Lidia -> KtorClient: $message")
                    }
                }

                level = LogLevel.NONE

            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:" , "${response.status.value}")
                }
            }
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause , _ ->
                    when (cause) {
                        is java.net.UnknownHostException -> throw Exception("Network connection error. Please check your internet connection.")
                        is io.ktor.client.plugins.ClientRequestException -> throw Exception("Client request failed: ${cause.message}")
                        is io.ktor.client.plugins.ServerResponseException -> throw Exception("Server error occurred: ${cause.message}")
                    }
                }
            }
        }
    }
}
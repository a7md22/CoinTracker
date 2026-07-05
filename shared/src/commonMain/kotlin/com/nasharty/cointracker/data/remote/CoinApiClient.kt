package com.nasharty.cointracker.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Core Network client wrapping Ktor HttpClient.
 * Configured with content negotiation, standard logging, and global request routing.
 */
class CoinApiClient(private val httpClient: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.coincap.io/v2/"
    }

    /**
     * Pre-configured Ktor client engine instance.
     * Injecting the HttpClient instead of hardcoding it enables proper testing (mocking)
     * and multiplatform engine injection (OkHttp for Android / Darwin for iOS).
     */
    val client = httpClient.config {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        defaultRequest {
            url(BASE_URL)
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }
    }
}
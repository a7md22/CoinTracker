package com.nasharty.cointracker.di

import androidx.room.Room
import com.nasharty.cointracker.data.local.CoinDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

/**
 * Android-specific dependencies implementation for Koin.
 */
actual val platformModule = module {
    // 1. Provide Android-specific Ktor HttpClient Engine (OkHttp)
    single<HttpClient> {
        HttpClient(OkHttp)
    }

    // 2. Provide Room Database Builder for Android (requires Application Context)
    single<CoinDatabase> {
        val context = get<android.content.Context>()
        val dbFile = context.getDatabasePath("coins.db")

        Room.databaseBuilder<CoinDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).build()
    }
}
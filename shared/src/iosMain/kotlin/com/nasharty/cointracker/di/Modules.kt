package com.nasharty.cointracker.di

import androidx.room.Room
import com.nasharty.cointracker.data.local.CoinDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

/**
 * iOS-specific dependencies implementation for Koin.
 */
actual val platformModule = module {
    // 1. Provide iOS-specific Ktor HttpClient Engine (Darwin)
    single<HttpClient> {
        HttpClient(Darwin)
    }

    // 2. Provide Room Database Builder for iOS
    single<CoinDatabase> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        val dbFilePath = documentDirectory?.path + "/coins.db"

        // Room now automatically triggers the internal factory behind the scenes
        Room.databaseBuilder<CoinDatabase>(
            name = dbFilePath
        ).build()
    }
}
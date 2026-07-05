package com.nasharty.cointracker

import android.app.Application
import com.nasharty.cointracker.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize the shared Koin DI container
        initKoin {
            // 1. Provide Android Logger for Koin logs inside Logcat
            androidLogger()

            // 2. Inject the Android Application Context into the Koin container
            androidContext(this@MainApplication)
        }
    }
}
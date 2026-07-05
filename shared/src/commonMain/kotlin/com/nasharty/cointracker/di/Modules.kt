package com.nasharty.cointracker.di

import com.nasharty.cointracker.data.local.CoinDao
import com.nasharty.cointracker.data.local.CoinDatabase
import com.nasharty.cointracker.data.remote.CoinApiClient
import com.nasharty.cointracker.data.remote.CoinApiService
import com.nasharty.cointracker.data.repository.CoinRepositoryImpl
import com.nasharty.cointracker.domain.repository.CoinRepository
import com.nasharty.cointracker.presentation.coinList.CoinListViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Shared Dependency Injection module for the common application logic.
 */
val sharedModule = module {
    // 1. Network Dependencies
    // We pass the platform-specific HttpClient (provided via platform modules later)
    single { CoinApiClient(httpClient = get()) }
    single<CoinDao> { get<CoinDatabase>().coinDao() }
    singleOf(::CoinApiService)

    // 2. Repository Dependency
    // Binds the Implementation (CoinRepositoryImpl) to its interface (CoinRepository)
    singleOf(::CoinRepositoryImpl) bind CoinRepository::class

    // 3. Register our ViewModel using factory
    // This creates a new instance of ViewModel whenever the screen requests it
    factory { CoinListViewModel(repository = get()) }
}

/**
 * Expect declaration for platform-specific dependencies (Network engine & Database builder).
 * Each platform (androidMain / iosMain) will provide its actual implementation.
 */
expect val platformModule: org.koin.core.module.Module

/**
 * Global entry point to initialize Koin Dependency Injection.
 * Used by iOS directly, and extended by Android to inject Application Context.
 */
fun initKoin(config: KoinAppDeclaration? = null): KoinApplication {
    return startKoin {
        // 1. Pass platform-specific configurations (like Android Context) if provided
        config?.invoke(this)

        // 2. Load all our DI Modules together
        modules(sharedModule, platformModule)
    }
}
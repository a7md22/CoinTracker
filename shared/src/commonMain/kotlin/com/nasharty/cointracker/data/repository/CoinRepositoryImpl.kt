package com.nasharty.cointracker.data.repository

import com.nasharty.cointracker.data.local.CoinDao
import com.nasharty.cointracker.data.mapper.toDomain
import com.nasharty.cointracker.data.mapper.toEntity
import com.nasharty.cointracker.data.remote.CoinApiService
import com.nasharty.cointracker.domain.model.Coin
import com.nasharty.cointracker.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Single source of truth repository that coordinates network requests and local database caching.
 * Implements the [CoinRepository] interface from the domain layer.
 */
class CoinRepositoryImpl(
    private val apiService: CoinApiService,
    private val coinDao: CoinDao
) : CoinRepository {

    override fun getCoins(): Flow<List<Coin>> {
        // Observes local database entities and maps them into pure domain models for the UI
        return coinDao.getCoins().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getCoinById(id: String): Coin? {
        // Standard placeholder for details fetching, will be bound to database/network logic later
        return null
    }

    override suspend fun syncCoins(): Result<Unit> {
        return runCatching {
            // 1. Fetch fresh data from the remote API service
            val response = apiService.fetchCoins()

            // 2. Map remote DTOs to local database entities via intermediate domain mapping
            val coinEntities = response.data.map { dto ->
                dto.toDomain().toEntity()
            }

            // 3. Update the local database cache (triggers subscribers via Room Flow automatically)
            coinDao.insertCoins(coinEntities)
        }
    }
}
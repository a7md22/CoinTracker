package com.nasharty.cointracker.domain.repository

import com.nasharty.cointracker.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    
    /**
     * Exposes a reactive stream of the coin list cached locally.
     * Follows the Single Source of Truth pattern.
     */
    fun getCoins(): Flow<List<Coin>>

    /**
     * Retrieves detailed information for a specific coin by its unique identifier.
     */
    suspend fun getCoinById(id: String): Coin?

    /**
     * Synchronizes local database cache with remote API data.
     */
    suspend fun syncCoins(): Result<Unit>
}
package com.nasharty.cointracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    /**
     * Observes the entire list of cached coins.
     * Returns a reactive [Flow] that emits updates whenever the database changes.
     */
    @Query("SELECT * FROM coins")
    fun getCoins(): Flow<List<CoinEntity>>

    /**
     * Inserts a list of coins into the local database.
     * Replaces existing entries if a conflict occurs (e.g., price updates).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: List<CoinEntity>)
}
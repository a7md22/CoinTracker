package com.nasharty.cointracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Entity representing the coins table in the local SQLite database.
 * Used for offline caching to ensure a Single Source of Truth.
 */
@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: Double,
    val marketCapUsd: Double,
    val changePercent24Hr: Double,
    val supply: Double
)
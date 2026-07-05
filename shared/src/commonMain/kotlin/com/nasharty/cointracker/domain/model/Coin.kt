package com.nasharty.cointracker.domain.model

/**
 * Represents the core business model for a cryptocurrency.
 * Isolated from data layer frameworks (Ktor/Room) to ensure domain purity.
 */
data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: Double,
    val marketCapUsd: Double,
    val changePercent24Hr: Double,
    val supply: Double
)
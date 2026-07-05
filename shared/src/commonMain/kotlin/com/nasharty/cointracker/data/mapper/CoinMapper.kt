package com.nasharty.cointracker.data.mapper

import com.nasharty.cointracker.data.remote.CoinDto
import com.nasharty.cointracker.domain.model.Coin
import com.nasharty.cointracker.data.local.CoinEntity

/**
 * Maps a [CoinDto] from the data layer into a clean [Coin] domain model.
 * Handles string-to-double parsing and provides safe fallbacks for null values.
 */
fun CoinDto.toDomain(): Coin {
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        priceUsd = priceUsd?.toDoubleOrNull() ?: 0.0,
        marketCapUsd = marketCapUsd?.toDoubleOrNull() ?: 0.0,
        changePercent24Hr = changePercent24Hr?.toDoubleOrNull() ?: 0.0,
        supply = supply?.toDoubleOrNull() ?: 0.0
    )
}

/**
 * Maps a [CoinEntity] from the local database into a clean [Coin] domain model.
 */
fun CoinEntity.toDomain(): Coin {
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        priceUsd = priceUsd,
        marketCapUsd = marketCapUsd,
        changePercent24Hr = changePercent24Hr,
        supply = supply
    )
}

/**
 * Maps a [Coin] domain model into a [CoinEntity] ready to be cached in the database.
 */
fun Coin.toEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        name = name,
        symbol = symbol,
        priceUsd = priceUsd,
        marketCapUsd = marketCapUsd,
        changePercent24Hr = changePercent24Hr,
        supply = supply
    )
}
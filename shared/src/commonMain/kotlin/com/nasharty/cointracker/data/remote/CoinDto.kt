package com.nasharty.cointracker.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinResponseDto(
    @SerialName("data") val data: List<CoinDto>
)

@Serializable
data class CoinDto(
    @SerialName("id") val id: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("name") val name: String,
    @SerialName("supply") val supply: String?,
    @SerialName("marketCapUsd") val marketCapUsd: String?,
    @SerialName("priceUsd") val priceUsd: String?,
    @SerialName("changePercent24Hr") val changePercent24Hr: String?
)
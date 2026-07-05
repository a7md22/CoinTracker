package com.nasharty.cointracker.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Handles remote API operations for cryptocurrency data using the configured Ktor client.
 */
class CoinApiService(private val apiClient: CoinApiClient) {

    /**
     * Fetches the top cryptocurrencies from the remote assets endpoint.
     * @return [CoinResponseDto] containing the list of remote coin data.
     */
    suspend fun fetchCoins(): CoinResponseDto {
        return apiClient.client.get("assets").body()
    }
}
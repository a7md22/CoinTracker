package com.nasharty.cointracker.presentation.coinList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasharty.cointracker.domain.model.Coin
import com.nasharty.cointracker.domain.repository.CoinRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel handling the Coin List screen state and logic.
 * Uses Jetpack Lifecycle ViewModel natively supported in KMP.
 */
class CoinListViewModel(
    private val repository: CoinRepository
) : ViewModel() {

    // 1. Convert the Cold Flow from Repository into a Hot StateFlow for the UI
    val uiState: StateFlow<CoinListUiState> = repository.getCoins()
        .map { coins ->
            if (coins.isEmpty()) {
                CoinListUiState.Empty
            } else {
                CoinListUiState.Success(coins = coins)
            }
        }
        .catch { exception ->
            emit(CoinListUiState.Error(message = exception.message ?: "An unknown error occurred"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CoinListUiState.Loading
        )

    init {
        // Trigger network sync immediately on startup
        syncData()
    }

    fun syncData() {
        viewModelScope.launch {
            try {
                repository.syncCoins()
            } catch (e: Exception) {
                // Background sync failed, UI will still show cached data
            }
        }
    }
}

/**
 * Represents the distinct states of the Coin List screen.
 */
sealed interface CoinListUiState {
    data object Loading : CoinListUiState
    data object Empty : CoinListUiState
    data class Success(val coins: List<Coin>) : CoinListUiState
    data class Error(val message: String) : CoinListUiState
}
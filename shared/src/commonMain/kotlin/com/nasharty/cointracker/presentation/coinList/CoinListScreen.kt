package com.nasharty.cointracker.presentation.coinList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nasharty.cointracker.domain.model.Coin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel,
    modifier: Modifier = Modifier
) {
    // 1. Collect the Hot StateFlow safely from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coin Tracker") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        // 2. Render UI based on the current sealed interface state
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is CoinListUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is CoinListUiState.Empty -> {
                    Text("No coins available. Pull to refresh!")
                }
                is CoinListUiState.Success -> {
                    CoinList(coins = state.coins)
                }
                is CoinListUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun CoinList(
    coins: List<Coin>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(coins, key = { it.id }) { coin ->
            CoinListItem(coin = coin)
        }
    }
}

@Composable
private fun CoinListItem(
    coin: Coin,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = coin.symbol.uppercase(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "$${coin.priceUsd.formatToTwoDecimals()}",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

expect fun Double.formatToTwoDecimals(): String
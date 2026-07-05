package com.nasharty.cointracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nasharty.cointracker.presentation.coinList.CoinListScreen
import com.nasharty.cointracker.presentation.coinList.CoinListViewModel
import org.koin.compose.koinInject

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // 1. Inject the ViewModel safely from Koin container in common code
            val viewModel = koinInject<CoinListViewModel>()

            // 2. Display our beautiful Screen
            CoinListScreen(viewModel = viewModel)
        }
    }
}
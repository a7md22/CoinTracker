package com.nasharty.cointracker.presentation.coinList

import java.util.Locale

// Implementation for Android (JVM)
actual fun Double.formatToTwoDecimals(): String {
    return String.format(Locale.US, "%.2f", this)
}
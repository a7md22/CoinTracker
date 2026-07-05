package com.nasharty.cointracker.presentation.coinList

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

// Implementation for iOS (Native)
actual fun Double.formatToTwoDecimals(): String {
    return NSString.stringWithFormat("%.2f", this)
}
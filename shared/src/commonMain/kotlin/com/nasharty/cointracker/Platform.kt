package com.nasharty.cointracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
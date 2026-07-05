package com.nasharty.cointracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room Database definition for the multiplatform application.
 * Provides abstract access to local DAOs.
 */
@Database(entities = [CoinEntity::class], version = 1)
abstract class CoinDatabase : RoomDatabase() {

    /**
     * Provides access to the Data Access Object (DAO) for coin operations.
     */
    abstract fun coinDao(): CoinDao
}
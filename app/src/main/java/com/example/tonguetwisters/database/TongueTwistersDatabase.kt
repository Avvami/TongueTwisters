package com.example.tonguetwisters.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TongueTwister::class], version = 1)
abstract class TongueTwistersDatabase: RoomDatabase() {

    abstract fun tongueTwistersDao(): TongueTwistersDatabaseDao
}
package com.example.tonguetwisters.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TongueTwistersDatabaseDao {

    @Update
    suspend fun updateTongueTwister(tongueTwister: TongueTwister)

    @Query("SELECT * FROM tongue_twisters WHERE genre = :genreFilter")
    fun getTongueTwistersOrderedByGenre(genreFilter: String): Flow<List<TongueTwister>>

    @Query("SELECT * FROM tongue_twisters WHERE favorite = :favoriteFilter")
    fun getTongueTwistersOrderedByFavorite(favoriteFilter: Boolean): Flow<List<TongueTwister>>

    @Query("SELECT * from tongue_twisters")
    fun getAllTongueTwisters(): Flow<List<TongueTwister>>
}
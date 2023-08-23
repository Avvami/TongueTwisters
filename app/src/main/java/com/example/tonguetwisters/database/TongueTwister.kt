package com.example.tonguetwisters.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tongue_twisters")
data class TongueTwister(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val content: String,
    val genre: String,
    val favorite: Boolean,
    val image_url: String,
    val english: Boolean
)
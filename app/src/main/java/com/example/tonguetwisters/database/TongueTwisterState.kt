package com.example.tonguetwisters.database

data class TongueTwistersState(
    val tongueTwisters: List<TongueTwister> = emptyList(),
    val sortType: SortType = SortType.ALL,
    val isDarkMode: Boolean = false
)

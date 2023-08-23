package com.example.tonguetwisters.database

sealed interface TongueTwistersEvent {
    data class UpdateTongueTwister(val tongueTwister: TongueTwister): TongueTwistersEvent
    data class SortTongueTwisters(val sortType: SortType): TongueTwistersEvent
    data class ToggleDarkMode(val darkMode: Boolean): TongueTwistersEvent
}
package com.example.tonguetwisters.database

enum class SortType {
    ALL,
    FAVORITE,
    LONG,
    SHORT,
    CHILDISH
}

fun SortType.toLocalizedString(): String {
    return when (this) {
        SortType.ALL -> "Все"
        SortType.FAVORITE -> "Любимые"
        SortType.LONG -> "Длинные"
        SortType.SHORT -> "Короткие"
        SortType.CHILDISH -> "Детские"
    }
}
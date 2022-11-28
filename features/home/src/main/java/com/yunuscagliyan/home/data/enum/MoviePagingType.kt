package com.yunuscagliyan.home.data.enum

enum class MoviePagingType(val index: Int) {
    UPCOMING(0),
    TRENDING(1),
    POPULAR(2),
    TOP_RATED(3);

    companion object {
        fun fromIndex(value: Int): MoviePagingType? = values().firstOrNull { it.index == value }
    }
}
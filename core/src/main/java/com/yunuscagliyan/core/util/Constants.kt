package com.yunuscagliyan.core.util

object Constants {

    object SharedPreferenceUtil {
        const val sharedPrefName = "app_shared_pref"
    }

    object StringParameter {
        const val EMPTY_STRING = ""
        const val RATE_LABEL = "/10"
    }

    object NetworkUtil {
        const val UPCOMING_MOVIES_URL = "movie/upcoming"
        const val POPULAR_MOVIES_URL = "movie/popular"
        const val TRENDING_MOVIES_URL = "trending/movie/day"
        const val TOP_RATED_MOVIES_URL = "movie/top_rated"
    }

    object NetworkQueryParamKey {
        const val page = "page"
        const val language = "language"
        const val region = "region"
        const val withGenres = "with_genres"
    }

    object PaginationUtil {
        const val PER_PAGE_ITEM = 20
    }
}
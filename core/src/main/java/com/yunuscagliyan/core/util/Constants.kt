package com.yunuscagliyan.core.util

object Constants {

    object SharedPreferenceUtil {
        const val sharedPrefName = "app_shared_pref"
    }

    object StringParameter {
        const val EMPTY_STRING = ""
        const val RATE_LABEL = "/10"
    }

    object DoubleFormatterUtil {
        const val DECIMAL_ONE = "%.1f"
        const val DECIMAL_TWO = "%.2f"
    }

    object DurationUTil {
        const val TRANSITION_DURATION = 500
        const val SPLASH_DURATION = 4000
        const val HOME_AUTO_SCROLL_DURATION = 8000
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

    object NavigationArgumentKey {
        const val LIST_TYPE_KEY = "list_type"
    }
}
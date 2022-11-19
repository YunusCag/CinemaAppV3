package com.yunuscagliyan.core.util

object Constants {

    object SharedPreferenceUtil {
        const val sharedPrefName = "app_shared_pref"
    }

    object StringParameter {
        const val EMPTY_STRING = ""
    }

    object NetworkUtil {
        const val UPCOMING_MOVIES_URL = "movie/upcoming"
        const val POPULAR_MOVIES_URL = "movie/popular"
        const val TOP_RATED_MOVIES_URL = "movie/top_rated"
        const val TRENDING_MOVIES_URL = "trending/movie/day"
    }

    object NetworkQueryParamKey {
        const val page = "page"
        const val language = "language"
        const val region = "region"
    }
}
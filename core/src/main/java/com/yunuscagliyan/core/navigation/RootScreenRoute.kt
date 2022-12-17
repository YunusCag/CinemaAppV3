package com.yunuscagliyan.core.navigation

import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.LIST_TYPE_KEY
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.MOVIE_ID_KEY

sealed class RootScreenRoute(val route: String) {
    object Splash : RootScreenRoute("splash_screen")
    object OnBoarding : RootScreenRoute("onboarding_screen")
    object Main : RootScreenRoute("main_screen")
    object MovieList : RootScreenRoute("movie_list_screen/{${LIST_TYPE_KEY}}") {
        fun navigate(listType: Int) = "movie_list_screen/$listType"
    }

    object MovieDetail : RootScreenRoute("movie_detail_screen/{${MOVIE_ID_KEY}}") {
        fun navigate(movieID: Int) = "movie_detail_screen/$movieID"
    }
}

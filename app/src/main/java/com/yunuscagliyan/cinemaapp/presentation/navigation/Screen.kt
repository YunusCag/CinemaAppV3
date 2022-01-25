package com.yunuscagliyan.cinemaapp.presentation.navigation


const val ROOT_GRAPH_ROUTE = "root"
const val HOME_GRAPH_ROUTE = "home"

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object MovieListScreen : Screen("movie_list_screen"){
        const val ARGUMENT_KEY_TYPE_ID = "list_type"
    }
    object MovieDetailScreen : Screen("movie_detail")
}

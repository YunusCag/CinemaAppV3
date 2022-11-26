package com.yunuscagliyan.core.navigation

sealed class MainScreenRoute(val route: String) {
    object Home : MainScreenRoute("home_screen")
    object Favourites : MainScreenRoute("favourites_screen")
    object Settings : MainScreenRoute("settings_screen")
}
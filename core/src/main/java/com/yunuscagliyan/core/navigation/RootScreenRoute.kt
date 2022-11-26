package com.yunuscagliyan.core.navigation

sealed class RootScreenRoute(val route: String) {
    object Splash : RootScreenRoute("splash_screen")
    object OnBoarding : RootScreenRoute("onboarding_screen")
    object Main : RootScreenRoute("main_screen")
}

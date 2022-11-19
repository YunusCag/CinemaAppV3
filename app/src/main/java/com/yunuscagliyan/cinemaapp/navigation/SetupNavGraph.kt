package com.yunuscagliyan.cinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yunuscagliyan.core.navigation.Screen
import com.yunuscagliyan.home.ui.pages.main.MainScreen
import com.yunuscagliyan.on_boarding.ui.OnBoardingScreen
import com.yunuscagliyan.splash.ui.SplashScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        SplashScreen.composable(
            this,
            navController
        )
        OnBoardingScreen.composable(
            this, navController
        )
        MainScreen.composable(
            this, navController
        )
    }

}
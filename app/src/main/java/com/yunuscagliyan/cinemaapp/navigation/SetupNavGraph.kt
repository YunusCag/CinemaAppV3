package com.yunuscagliyan.cinemaapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.yunuscagliyan.core.ui.navigation.Screen
import com.yunuscagliyan.on_boarding.ui.OnBoardingScreen
import com.yunuscagliyan.splash.ui.SplashScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen() { shouldShowOnBoarding ->
                navController.popBackStack()
                if (shouldShowOnBoarding) {
                    navController.navigate(Screen.OnBoarding.route)
                } else {
                    navController.navigate(Screen.Home.route)
                }

            }
        }
        composable(route = Screen.OnBoarding.route) {
            OnBoardingScreen() {
                navController.navigate(Screen.Home.route)
            }
        }
        composable(route = Screen.Home.route) {
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxSize()
            ) {

            }
        }
    }

}
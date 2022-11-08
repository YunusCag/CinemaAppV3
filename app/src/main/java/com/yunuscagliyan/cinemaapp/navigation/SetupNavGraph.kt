package com.yunuscagliyan.cinemaapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yunuscagliyan.core.navigation.Screen
import com.yunuscagliyan.on_boarding.ui.OnBoardingPage
import com.yunuscagliyan.splash.ui.SplashPage

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashPage() { shouldShowOnBoarding ->
                navController.popBackStack()
                if (shouldShowOnBoarding) {
                    navController.navigate(Screen.OnBoarding.route)
                } else {
                    navController.navigate(Screen.Home.route)
                }
            }
        }
        composable(route = Screen.OnBoarding.route) {
            OnBoardingPage() {
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
package com.yunuscagliyan.cinemaapp.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core.util.Constants.DurationUTil.TRANSITION_DURATION
import com.yunuscagliyan.core_ui.navigation.NavigationAnimDirection
import com.yunuscagliyan.home.home.ui.pages.main.MainScreen
import com.yunuscagliyan.home.movie_list.ui.MovieListScreen
import com.yunuscagliyan.movie_detail.ui.MovieDetailScreen
import com.yunuscagliyan.on_boarding.ui.OnBoardingScreen
import com.yunuscagliyan.splash.ui.SplashScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = RootScreenRoute.Splash.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(TRANSITION_DURATION, easing = LinearEasing)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(TRANSITION_DURATION, easing = LinearEasing)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(TRANSITION_DURATION, easing = LinearEasing)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(TRANSITION_DURATION, easing = LinearEasing)
            )
        }
    ) {
        SplashScreen.composable(
            this,
            navController,
        )
        OnBoardingScreen.composable(
            this,
            navController
        )
        MainScreen.composable(
            this,
            navController,
        )
        MovieListScreen.composable(
            this,
            navController
        )
        MovieDetailScreen.composable(
            this,
            navController
        )
    }

}
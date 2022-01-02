package com.yunuscagliyan.cinemaapp.presentation.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yunuscagliyan.cinemaapp.presentation.navigation.HOME_GRAPH_ROUTE
import com.yunuscagliyan.cinemaapp.presentation.navigation.Screen
import com.yunuscagliyan.cinemaapp.presentation.screens.home.HomeScreen


fun NavGraphBuilder.movieNavGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = Screen.Home.route,
        route = HOME_GRAPH_ROUTE
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navHostController)
        }
    }
}
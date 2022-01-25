package com.yunuscagliyan.cinemaapp.presentation.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.yunuscagliyan.cinemaapp.domain.enum.MovieListType
import com.yunuscagliyan.cinemaapp.presentation.navigation.HOME_GRAPH_ROUTE
import com.yunuscagliyan.cinemaapp.presentation.navigation.Screen
import com.yunuscagliyan.cinemaapp.presentation.screens.home.HomeScreen
import com.yunuscagliyan.cinemaapp.presentation.screens.movie_list.MovieListScreen


@ExperimentalCoilApi
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
        composable(
            route = Screen.MovieListScreen.route+"/{${Screen.MovieListScreen.ARGUMENT_KEY_TYPE_ID}}",
            arguments = listOf(
                navArgument(Screen.MovieListScreen.ARGUMENT_KEY_TYPE_ID){
                    type= NavType.IntType

                }
            )
        ){
            MovieListScreen(
                navController = navHostController,
            )
        }
    }
}
package com.yunuscagliyan.cinemaapp.presentation.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yunuscagliyan.cinemaapp.presentation.navigation.HOME_GRAPH_ROUTE
import com.yunuscagliyan.cinemaapp.presentation.navigation.ROOT_GRAPH_ROUTE

@Composable
fun SetupNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = HOME_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        movieNavGraph(
            navHostController
        )
    }

}
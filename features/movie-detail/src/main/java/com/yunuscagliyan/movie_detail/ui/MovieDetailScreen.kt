package com.yunuscagliyan.movie_detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.MOVIE_ID_KEY
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.movie_detail.viewmodel.MovieDetailViewModel

object MovieDetailScreen : CoreScreen<MovieDetailViewModel>() {
    override val route: String = RootScreenRoute.MovieDetail.route

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(
            name = MOVIE_ID_KEY
        ) {
            type = NavType.IntType
        }
    )

    @Composable
    override fun viewModel(): MovieDetailViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: MovieDetailViewModel) {
        val state by viewModel.state

    }
}
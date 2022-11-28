package com.yunuscagliyan.home.movie_list.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core_ui.components.header.SimpleTopBar
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.LIST_TYPE_KEY
import com.yunuscagliyan.home.data.enum.MoviePagingType
import com.yunuscagliyan.home.movie_list.viewmodel.MovieListViewModel


object MovieListScreen : CoreScreen<MovieListViewModel>() {
    override val route: String
        get() = RootScreenRoute.MovieList.route

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(
            name = LIST_TYPE_KEY,
        ) {
            type = NavType.IntType
            defaultValue = MoviePagingType.UPCOMING.index
        }
    )

    @Composable
    override fun viewModel(): MovieListViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: MovieListViewModel) {
        val state by viewModel.state

        MainUIFrame(
            topBar = {
                TopBar(
                    moviePagingType = state.listType
                )
            }
        ) {

        }
    }

    @Composable
    private fun TopBar(moviePagingType: MoviePagingType) {
        SimpleTopBar(
            title = stringResource(
                id = when (moviePagingType) {
                    MoviePagingType.UPCOMING -> R.string.up_coming_movie_text
                    MoviePagingType.TRENDING -> R.string.trending_title_text
                    MoviePagingType.POPULAR -> R.string.popular_title_text
                    MoviePagingType.TOP_RATED -> R.string.top_rated_title_text
                }
            )
        )
    }
}
package com.yunuscagliyan.home.ui.pages.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.yunuscagliyan.core_ui.components.list.HorizontalMovieListView
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.navigation.MainScreenRoute
import com.yunuscagliyan.core_ui.components.header.SimpleTopBar
import com.yunuscagliyan.core_ui.components.pager.MovieHorizontalPager
import com.yunuscagliyan.home.viewmodel.home.HomeViewModel

object HomeScreen : CoreScreen<HomeViewModel>() {
    override val route: String
        get() = MainScreenRoute.Home.route

    @Composable
    override fun viewModel(): HomeViewModel = hiltViewModel()

    @OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
    @Composable
    override fun Content(viewModel: HomeViewModel) {
        val upComingMovies = viewModel.upComingMovies

        val trendingMovies = viewModel.trendingMovies

        val popularMovies = viewModel.popularMovies

        val topRatedMovies = viewModel.topRatedMovies

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MovieHorizontalPager(
                movies = upComingMovies,
            )

            HorizontalMovieListView(
                movies = trendingMovies,
                title = stringResource(R.string.home_page_trending_title_text),
                onTap = {

                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalMovieListView(
                movies = popularMovies,
                title = stringResource(R.string.home_page_popular_title_text),
                onTap = {

                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalMovieListView(
                movies = topRatedMovies,
                title = stringResource(R.string.home_page_top_rated_title_text),
                onTap = {

                }
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .navigationBarsPadding(),
            )
        }
    }
}
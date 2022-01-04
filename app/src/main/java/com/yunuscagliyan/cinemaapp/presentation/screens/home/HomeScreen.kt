package com.yunuscagliyan.cinemaapp.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.yunuscagliyan.cinemaapp.R
import com.yunuscagliyan.cinemaapp.presentation.common.components.list.HorizontalMovieListView
import com.yunuscagliyan.cinemaapp.presentation.screens.home.components.HomeTopBar
import com.yunuscagliyan.cinemaapp.presentation.screens.home.components.MovieHorizontalPager

@ExperimentalCoilApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val upComingState = viewModel.upComingState.value
    val upComingMovies = viewModel.upComingMovies.value

    val trendingState = viewModel.trendingState.value
    val trendingMovies = viewModel.trendingMovies.value

    val popularState = viewModel.popularState.value
    val popularMovies = viewModel.popularMovies.value

    val topRatedState = viewModel.topRatedState.value
    val topRatedMovies = viewModel.topRatedMovies.value

    Scaffold(
        topBar = { HomeTopBar() },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            MovieHorizontalPager(
                state = upComingState,
                movies = upComingMovies,
            )
            HorizontalMovieListView(
                state = trendingState,
                movies = trendingMovies,
                title = stringResource(R.string.home_page_trending_title_text),
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalMovieListView(
                state = popularState,
                movies = popularMovies,
                title = stringResource(R.string.home_page_popular_title_text),
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalMovieListView(
                state = topRatedState,
                movies = topRatedMovies,
                title = stringResource(R.string.home_page_top_rated_title_text),
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

    }

}
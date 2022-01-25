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
import com.yunuscagliyan.cinemaapp.domain.enum.MovieListType
import com.yunuscagliyan.cinemaapp.presentation.common.components.list.HorizontalMovieListView
import com.yunuscagliyan.cinemaapp.presentation.navigation.Screen
import com.yunuscagliyan.cinemaapp.presentation.screens.home.components.HomeTopBar
import com.yunuscagliyan.cinemaapp.presentation.screens.home.components.MovieHorizontalPager

@ExperimentalCoilApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val upComingMovies = viewModel.upComingMovies

    val trendingMovies = viewModel.trendingMovies

    val popularMovies = viewModel.popularMovies

    val topRatedMovies = viewModel.topRatedMovies

    Scaffold(
        topBar = { HomeTopBar() },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            MovieHorizontalPager(
                movies = upComingMovies,
            )
            HorizontalMovieListView(
                movies = trendingMovies,
                title = stringResource(R.string.home_page_trending_title_text),
                onTap = {
                    navController
                        .navigate(Screen.MovieListScreen.route + "/${MovieListType.TRENDING.index}")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalMovieListView(
                movies = popularMovies,
                title = stringResource(R.string.home_page_popular_title_text),
                onTap = {
                    navController
                        .navigate(Screen.MovieListScreen.route + "/${MovieListType.POPULAR.index}")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalMovieListView(
                movies = topRatedMovies,
                title = stringResource(R.string.home_page_top_rated_title_text),
                onTap = {
                    navController
                        .navigate(Screen.MovieListScreen.route + "/${MovieListType.TOP_RATED.index}")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

    }

}
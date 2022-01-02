package com.yunuscagliyan.cinemaapp.presentation.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.yunuscagliyan.cinemaapp.presentation.screens.home.components.HomeTopBar
import com.yunuscagliyan.cinemaapp.presentation.screens.home.components.MovieHorizontalPager

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.upComingState.value
    val upComingMovies = viewModel.upComingMovies.value

    Scaffold(
        topBar = { HomeTopBar() },
    ) {
        MovieHorizontalPager(
            state = state,
            movies = upComingMovies,
        )
    }

}
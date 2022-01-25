package com.yunuscagliyan.cinemaapp.presentation.screens.movie_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.yunuscagliyan.cinemaapp.domain.enum.MovieListType
import com.yunuscagliyan.cinemaapp.presentation.screens.movie_list.components.MovieGridView
import com.yunuscagliyan.cinemaapp.presentation.screens.movie_list.components.MovieListTopBar


@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val movies = viewModel.movies
    val listType = MovieListType.values()[viewModel.listType ?: 0]



    Scaffold(
        topBar = {
            MovieListTopBar(
                listType = listType,
                navController = navController,
            )
        }
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            MovieGridView(
                moviesFlow = movies,

                )
        }

    }
}
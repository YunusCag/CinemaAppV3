package com.yunuscagliyan.cinemaapp.presentation.screens.movie_list.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.yunuscagliyan.cinemaapp.R
import com.yunuscagliyan.cinemaapp.domain.enum.MovieListType


@Composable
fun MovieListTopBar(
    listType: MovieListType,
    navController: NavController
) {
    val title = when (listType) {
        MovieListType.UP_COMING -> stringResource(R.string.up_coming_movie_text)
        MovieListType.TRENDING -> stringResource(R.string.home_page_trending_title_text)
        MovieListType.POPULAR -> stringResource(R.string.home_page_popular_title_text)
        MovieListType.TOP_RATED -> stringResource(R.string.home_page_top_rated_title_text)
    }


    TopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button",

                )
            }
        }
    )

}
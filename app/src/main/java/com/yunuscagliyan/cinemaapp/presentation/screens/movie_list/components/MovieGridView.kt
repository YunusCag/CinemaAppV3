package com.yunuscagliyan.cinemaapp.presentation.screens.movie_list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
import com.yunuscagliyan.cinemaapp.data.remote.url.POSTER_IMAGE_URL
import com.yunuscagliyan.cinemaapp.presentation.common.components.anim.LoadingAnimation
import com.yunuscagliyan.cinemaapp.presentation.common.components.error.NetworkErrorView
import com.yunuscagliyan.cinemaapp.presentation.common.components.list.MovieSmallCardSkeleton
import com.yunuscagliyan.cinemaapp.presentation.common.components.shimmer.AnimatedShimmer
import kotlinx.coroutines.flow.Flow

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun MovieGridView(
    moviesFlow: Flow<PagingData<MovieModel>>,
    modifier: Modifier = Modifier
) {
    val movies = moviesFlow.collectAsLazyPagingItems()


    Column {
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            modifier = modifier,
        ) {
            items(movies.itemCount) { index ->
                val movie = movies[index]
                MovieGridItem(
                    movie = movie
                )
            }
        }
        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(3),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
                        modifier = modifier,
                    ) {
                        items(30) {
                            AnimatedShimmer {
                                Box(
                                    modifier = Modifier
                                        .height(150.dp)
                                        .padding(horizontal = 4.dp, vertical = 4.dp)
                                        .clip(
                                            RoundedCornerShape(8.dp)
                                        )
                                        .background(it),
                                ) {

                                }
                            }
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val errorState = loadState.refresh as LoadState.Error

                    NetworkErrorView(
                        message = errorState.error.message
                    )
                }
                loadState.append is LoadState.Loading -> {
                    LoadingAnimation(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                loadState.append is LoadState.Error -> {
                    val errorState = loadState.append as LoadState.Error

                    NetworkErrorView(
                        message = errorState.error.message
                    )
                }
            }
        }

    }
}

@ExperimentalCoilApi
@Composable
fun MovieGridItem(
    movie: MovieModel?,
) {
    Image(
        painter = rememberImagePainter("$POSTER_IMAGE_URL${movie?.posterPath ?: ""}"),
        contentDescription = movie?.title ?: "",
        modifier = Modifier
            .height(150.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clip(
                RoundedCornerShape(8.dp)
            ),
        contentScale = ContentScale.Crop,
    )
}
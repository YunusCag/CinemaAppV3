package com.yunuscagliyan.cinemaapp.presentation.common.components.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
import com.yunuscagliyan.cinemaapp.data.remote.url.POSTER_IMAGE_URL
import com.yunuscagliyan.cinemaapp.presentation.common.components.anim.LoadingAnimation
import com.yunuscagliyan.cinemaapp.presentation.common.components.error.NetworkErrorView
import com.yunuscagliyan.cinemaapp.presentation.common.components.label.MovieRateLabel
import com.yunuscagliyan.cinemaapp.presentation.common.components.shimmer.AnimatedShimmer
import kotlinx.coroutines.flow.Flow


@ExperimentalCoilApi
@Composable
fun HorizontalMovieListView(
    movies: Flow<PagingData<MovieModel>>,
    title: String,
    onTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyMovieItems: LazyPagingItems<MovieModel> = movies.collectAsLazyPagingItems()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable {
                    onTap()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5
            )
            Icon(
                Icons.Default.KeyboardArrowRight,
                "arrow"
            )
        }
        Row(
            modifier = modifier
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                items(lazyMovieItems.itemCount) { index ->
                    val movie = lazyMovieItems[index]
                    MovieSmallCard(
                        movie = movie
                    )
                }
            }

            lazyMovieItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        LazyRow(
                            modifier = Modifier
                                .padding(start = 8.dp)
                        ) {
                            items(10) {
                                MovieSmallCardSkeleton()
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
}

@ExperimentalCoilApi
@Composable
fun MovieSmallCard(
    movie: MovieModel?,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .padding(end = 8.dp),

        ) {
        Card(
            modifier = Modifier
                .height(150.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            Image(
                painter = rememberImagePainter("$POSTER_IMAGE_URL${movie?.posterPath ?: ""}"),
                contentDescription = movie?.title ?: "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxSize()
            ) {
                MovieRateLabel(voteAverage = movie?.voteAverage)
            }

        }
        Text(
            text = movie?.title ?: "",
            style = MaterialTheme.typography.body2,
            maxLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.height(30.dp)
        )
    }
}

@Composable
fun MovieSmallCardSkeleton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .padding(end = 8.dp),

        ) {
        Card(
            modifier = Modifier
                .height(150.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            AnimatedShimmer {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(it)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AnimatedShimmer {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(it)
            )
        }
    }
}
package com.yunuscagliyan.cinemaapp.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
import com.yunuscagliyan.cinemaapp.data.remote.url.POSTER_IMAGE_URL
import com.yunuscagliyan.cinemaapp.presentation.common.components.label.MovieRateLabel
import com.yunuscagliyan.cinemaapp.presentation.state.NetworkState

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun MovieHorizontalPager(
    state: NetworkState,
    movies: List<MovieModel?>,
    title: String = "Up Coming"
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
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
        when (state) {
            is NetworkState.Error -> {
                Text(
                    text = state.message ?: "",

                    )
            }
            NetworkState.Loading -> {
                CircularProgressIndicator()
            }
            NetworkState.Success -> {
                val pagerState = rememberPagerState()

                HorizontalPager(
                    count = movies.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),

                    ) { index ->
                    val movie = movies[index]
                    MovieHorizontalPage(
                        movie = movie
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun MovieHorizontalPage(
    movie: MovieModel?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(
            8.dp
        ),
    ) {
        Image(
            painter = rememberImagePainter("$POSTER_IMAGE_URL${movie?.backdropPath ?: ""}"),
            contentDescription = movie?.title ?: "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomStart,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black,
                            )
                        )
                    )
                    .padding(8.dp)

            ) {
                Text(
                    text = movie?.title ?: "",
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.White,
                    ),
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd,
        ) {
            MovieRateLabel(
                voteAverage = movie?.voteAverage,
                modifier = Modifier.padding(
                    end = 8.dp,
                    top = 8.dp,
                )
            )
        }
    }

}
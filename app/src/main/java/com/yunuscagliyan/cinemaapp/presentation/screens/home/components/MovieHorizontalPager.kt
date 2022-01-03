package com.yunuscagliyan.cinemaapp.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.rememberPagerState
import com.yunuscagliyan.cinemaapp.R
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
import com.yunuscagliyan.cinemaapp.data.remote.url.POSTER_IMAGE_URL
import com.yunuscagliyan.cinemaapp.presentation.common.components.label.MovieRateLabel
import com.yunuscagliyan.cinemaapp.presentation.common.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.cinemaapp.presentation.state.NetworkState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun MovieHorizontalPager(
    state: NetworkState,
    movies: List<MovieModel?>,
    title: String = stringResource(id = R.string.up_coming_movie_text)
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
                    text = state.message
                        ?: stringResource(id = R.string.common_unknown_error_message),
                    style = MaterialTheme.typography.h6,
                )
            }
            NetworkState.Loading -> {
                AnimatedShimmer {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(it)
                    )
                }
            }
            NetworkState.Success -> {
                val pagerState = rememberPagerState()

                LaunchedEffect(key1 = Unit){
                    while (true){
                        yield()
                        delay(2000L)
                        pagerState.animateScrollToPage(
                            page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                        )
                    }
                }
                HorizontalPager(
                    count = movies.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),

                    ) { index ->
                    val movie = movies[index]
                    MovieHorizontalPage(
                        movie = movie,
                        pageOffset = calculateCurrentOffsetForPage(index).absoluteValue
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
    pageOffset:Float,
) {
    Card(
        modifier = modifier
            .graphicsLayer {
                // We animate the scaleX + scaleY, between 85% and 100%
                lerp(
                    start = ScaleFactor(0.70f,0.70f),
                    stop = ScaleFactor(1f,1f),
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale.scaleX
                    scaleY = scale.scaleX
                }
            }
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

@ExperimentalPagerApi
fun PagerScope.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage + currentPageOffset) - page
}
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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.rememberPagerState
import com.yunuscagliyan.cinemaapp.R
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
import com.yunuscagliyan.cinemaapp.data.remote.url.POSTER_IMAGE_URL
import com.yunuscagliyan.cinemaapp.presentation.common.components.error.NetworkErrorView
import com.yunuscagliyan.cinemaapp.presentation.common.components.label.MovieRateLabel
import com.yunuscagliyan.cinemaapp.presentation.common.components.shimmer.AnimatedShimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun MovieHorizontalPager(
    movies: Flow<PagingData<MovieModel>>,
    title: String = stringResource(id = R.string.up_coming_movie_text),
    modifier: Modifier = Modifier
) {
    val lazyMovieItems: LazyPagingItems<MovieModel> = movies.collectAsLazyPagingItems()


    Column(
        modifier = modifier
    ) {
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
        Column(
            modifier = modifier.height(200.dp)

        ) {
            val pagerState = rememberPagerState()

            LaunchedEffect(key1 = Unit) {
                while (true) {
                    yield()
                    delay(2000L)
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                    )
                }
            }
            HorizontalPager(
                count = lazyMovieItems.itemCount,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()

            ) { index ->
                val movie = lazyMovieItems[index]
                MovieHorizontalPage(
                    movie = movie,
                    pageOffset = calculateCurrentOffsetForPage(index).absoluteValue
                )
            }

            lazyMovieItems.apply {
                when{
                    loadState.refresh is LoadState.Loading->{
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
                    loadState.refresh is LoadState.Error->{
                        val errorState=loadState.refresh as LoadState.Error

                        NetworkErrorView(
                            message = errorState.error.message
                        )
                    }
                    loadState.append is LoadState.Loading->{
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
                    loadState.append is LoadState.Error->{
                        val errorState=loadState.append as LoadState.Error

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
fun MovieHorizontalPage(
    movie: MovieModel?,
    modifier: Modifier = Modifier,
    pageOffset: Float,
) {
    Card(
        modifier = modifier
            .graphicsLayer {
                // We animate the scaleX + scaleY, between 85% and 100%
                lerp(
                    start = ScaleFactor(0.70f, 0.70f),
                    stop = ScaleFactor(1f, 1f),
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
            contentScale = ContentScale.FillBounds,
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
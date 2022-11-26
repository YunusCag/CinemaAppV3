package com.yunuscagliyan.core_ui.components.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.rememberPagerState
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.util.Constants.DurationUTil.HOME_AUTO_SCROLL_DURATION
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.label.MovieRateLabel
import com.yunuscagliyan.core_ui.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
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
    modifier: Modifier = Modifier,
    onTap: () -> Unit,
) {
    val lazyMovieItems: LazyPagingItems<MovieModel> = movies.collectAsLazyPagingItems()


    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    onTap()
                }
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = CinemaAppTheme.typography.title,
                color = CinemaAppTheme.colors.textPrimary
            )

            Icon(
                Icons.Default.KeyboardArrowRight,
                "arrow"
            )
        }
        Column(
            modifier = modifier.height(250.dp)
        ) {
            val pagerState = rememberPagerState()

            LaunchedEffect(key1 = Unit) {
                while (true) {
                    yield()
                    delay(HOME_AUTO_SCROLL_DURATION.toLong())
                    if (pagerState.pageCount > pagerState.currentPage) {
                        pagerState.animateScrollToPage(
                            page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                        )
                    }
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
                when {
                    loadState.refresh is LoadState.Loading -> {
                        AnimatedShimmer {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(it)
                            )
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val errorState = loadState.refresh as LoadState.Error

                        NetworkErrorView(
                            message = errorState.error.message
                        )
                    }
                    loadState.append is LoadState.Loading -> {
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
private fun MovieHorizontalPage(
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
        shape = CinemaAppTheme.shapes.defaultMediumShape,
        backgroundColor = Color.Unspecified,
    ) {
        AppImage(
            modifier = Modifier.fillMaxSize(),
            url = movie?.posterPath,
            description = movie?.title,
            contentScale = ContentScale.FillBounds
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
                    style = CinemaAppTheme.typography.normalText,
                    color = CinemaAppTheme.colors.whiteColor
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
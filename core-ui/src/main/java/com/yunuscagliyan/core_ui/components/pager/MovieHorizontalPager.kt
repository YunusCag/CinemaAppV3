package com.yunuscagliyan.core_ui.components.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
    onMovieTap: (MovieModel?) -> Unit,
    onListTap: () -> Unit,
) {
    val lazyMovieItems: LazyPagingItems<MovieModel> = movies.collectAsLazyPagingItems()


    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    onListTap()
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
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = CinemaAppTheme.colors.textPrimary
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

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp
                    ),
                shape = CinemaAppTheme.shapes.defaultLargeShape,
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth(),
                    count = lazyMovieItems.itemCount,
                    state = pagerState,
                ) { index ->
                    val movie = lazyMovieItems[index]
                    MovieHorizontalPage(
                        movie = movie,
                        onTap = {
                            onMovieTap(movie)
                        }
                    )
                }
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
                            message = errorState.error.message,
                            onRefreshClick = {
                                lazyMovieItems.refresh()
                            }
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
    onTap: () -> Unit
) {
    Box(
        modifier = modifier
            .noRippleClickable {
                onTap()
            },
    ) {
        AppImage(
            modifier = Modifier.fillMaxSize(),
            url = movie?.posterPath,
            description = movie?.title,
            contentScale = ContentScale.Crop
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
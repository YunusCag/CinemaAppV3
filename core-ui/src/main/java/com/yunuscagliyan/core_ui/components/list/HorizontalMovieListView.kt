package com.yunuscagliyan.core_ui.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.label.MovieRateLabel
import com.yunuscagliyan.core_ui.components.loading.LoadingView
import com.yunuscagliyan.core_ui.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import kotlinx.coroutines.flow.Flow

@ExperimentalCoilApi
@Composable
fun HorizontalMovieListView(
    modifier: Modifier = Modifier,
    movies: Flow<PagingData<MovieModel>>,
    title: String,
    titleTextStyle: TextStyle = CinemaAppTheme.typography.title,
    titleColor: Color = CinemaAppTheme.colors.textPrimary,
    titlePadding: PaddingValues = PaddingValues(
        horizontal = 8.dp, vertical = 12.dp
    ),
    listPadding: PaddingValues = PaddingValues(
        start = 8.dp
    ),
    empty: @Composable () -> Unit = {},
    onMovieTap: (MovieModel?) -> Unit,
    onListTap: (() -> Unit)? = null,
) {
    val lazyMovieItems: LazyPagingItems<MovieModel> = movies.collectAsLazyPagingItems()
    val lazyState = rememberLazyListState()

    if (lazyMovieItems.itemCount == 0 && lazyMovieItems.loadState.refresh != LoadState.Loading) {
        empty()
    } else {
        Column(
            modifier = modifier,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable {
                        onListTap?.invoke()
                    }
                    .padding(
                        titlePadding
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    style = titleTextStyle,
                    color = titleColor
                )
                if (onListTap != null) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        tint = CinemaAppTheme.colors.textPrimary
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                LazyRow(
                    contentPadding = listPadding,
                    state = lazyState
                ) {
                    items(lazyMovieItems.itemCount) { index ->
                        val movie = lazyMovieItems[index]
                        MovieSmallCard(
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
                            LazyRow(
                                contentPadding = listPadding
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
                            LoadingView(
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
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
private fun MovieSmallCard(
    movie: MovieModel?,
    onTap: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .padding(end = 8.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = CinemaAppTheme.shapes.defaultSmallShape,
            backgroundColor = Color.Unspecified,
            elevation = 0.dp,
            onClick = onTap
        ) {
            AppImage(
                modifier = Modifier.fillMaxSize(),
                url = movie?.posterPath,
                description = movie?.title,
            )

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                MovieRateLabel(
                    voteAverage = movie?.voteAverage,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie?.title ?: "",
            style = CinemaAppTheme.typography.normalText,
            color = CinemaAppTheme.colors.textPrimary,
            maxLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun MovieSmallCardSkeleton() {
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
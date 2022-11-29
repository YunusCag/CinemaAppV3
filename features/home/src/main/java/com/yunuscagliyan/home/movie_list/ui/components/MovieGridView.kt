package com.yunuscagliyan.home.movie_list.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.label.MovieRateLabel
import com.yunuscagliyan.core_ui.components.loading.LoadingView
import com.yunuscagliyan.core_ui.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.home.data.enum.MovieListGridColumn
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieGridView(
    modifier: Modifier = Modifier,
    moviesFlow: Flow<PagingData<MovieModel>>,
    column: MovieListGridColumn,
    onMovieTap: (MovieModel?) -> Unit
) {
    val movies = moviesFlow.collectAsLazyPagingItems()
    Column {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(column.columnCount),
            modifier = modifier,
        ) {
            items(movies.itemCount) { index ->
                val movie = movies[index]
                if (column == MovieListGridColumn.SINGLE_ITEM) {
                    MovieGridItemLarge(
                        model = movie
                    )
                } else {
                    MovieGridItemSmall(
                        movieModel = movie,
                        onTap = {
                            onMovieTap(movie)
                        }
                    )
                }
            }
        }
        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
                        modifier = modifier,
                    ) {
                        items(30) {
                            AnimatedShimmer {
                                Box(
                                    modifier = Modifier
                                        .heightIn(min = 150.dp)
                                        .background(it),
                                )
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
                    LoadingView(
                        modifier = Modifier
                            .height(150.dp)
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

@Composable
private fun MovieGridItemSmall(
    movieModel: MovieModel?,
    onTap: () -> Unit
) {
    AppImage(
        modifier = Modifier
            .heightIn(min = 150.dp)
            .noRippleClickable {
                onTap()
            },
        url = movieModel?.posterPath,
        description = movieModel?.title,
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun MovieGridItemLarge(
    model: MovieModel?
) {
    val imageWidth = 140.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 12.dp
            ),
        contentAlignment = Alignment.TopStart
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 24.dp),
            shape = CinemaAppTheme.shapes.defaultSmallShape,
            backgroundColor = CinemaAppTheme.colors.card,
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = imageWidth
                    )
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(
                    text = model?.title ?: EMPTY_STRING,
                    style = CinemaAppTheme.typography.normalText.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = CinemaAppTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                MovieRateLabel(
                    voteAverage = model?.voteAverage
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = model?.overview ?: EMPTY_STRING,
                    style = CinemaAppTheme.typography.smallText1,
                    color = CinemaAppTheme.colors.secondaryGray,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Card(
            modifier = Modifier
                .width(imageWidth)
                .height(150.dp)
                .padding(
                    start = 8.dp,
                    bottom = 8.dp,
                ),
            backgroundColor = Color.Unspecified,
            shape = CinemaAppTheme.shapes.defaultSmallShape,
            elevation = 0.dp
        ) {
            AppImage(
                modifier = Modifier.fillMaxSize(),
                url = model?.posterPath,
                description = model?.title,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}
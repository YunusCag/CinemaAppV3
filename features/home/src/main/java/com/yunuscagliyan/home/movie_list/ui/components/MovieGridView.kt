package com.yunuscagliyan.home.movie_list.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core_ui.components.card.MovieLargeCard
import com.yunuscagliyan.core_ui.components.empty.NetworkEmptyView
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.home.data.enum.MovieListGridColumn

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieGridView(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieModel>,
    column: MovieListGridColumn,
    contentPaddingValues: PaddingValues = PaddingValues(
        bottom = 60.dp
    ),
    onRefreshClick: () -> Unit,
    onMovieTap: (MovieModel?) -> Unit
) {
    val columnCount =
        if (movies.itemCount == 1 && column.columnCount == MovieListGridColumn.COLUMN_THIRD.columnCount) {
            column.columnCount - 1
        } else {
            column.columnCount
        }

    Column(
        modifier = modifier
    ) {
        if (movies.itemCount > 0) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .weight(1f),
                columns = StaggeredGridCells.Fixed(columnCount),
                contentPadding = PaddingValues(
                    top = if (column == MovieListGridColumn.SINGLE_ITEM) 60.dp else 0.dp,
                    bottom = 10.dp
                )
            ) {
                items(movies.itemCount) { index ->
                    val movie = movies[index]
                    if (column == MovieListGridColumn.SINGLE_ITEM) {
                        MovieLargeCard(
                            model = movie,
                            onTap = {
                                onMovieTap(movie)
                            }
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
        } else {
            NetworkEmptyView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    val isSingleItem = column == MovieListGridColumn.SINGLE_ITEM
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(column.columnCount),
                        modifier = Modifier.fillMaxSize(),
                        verticalItemSpacing = if (isSingleItem) 12.dp else 0.dp,
                        horizontalArrangement = Arrangement.spacedBy(if (isSingleItem) 8.dp else 0.dp),
                        contentPadding = contentPaddingValues
                    ) {
                        items(30) {
                            AnimatedShimmer {
                                Box(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = if (isSingleItem) 8.dp else 0.dp
                                        )
                                        .fillMaxWidth()
                                        .heightIn(min = 150.dp)
                                        .background(
                                            it,
                                            shape = if (isSingleItem) CinemaAppTheme.shapes.defaultSmallShape else CinemaAppTheme.shapes.nonShape
                                        ),
                                )
                            }
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val errorState = loadState.refresh as LoadState.Error

                    NetworkErrorView(
                        message = errorState.error.message,
                        onRefreshClick = {
                            movies.refresh()
                            onRefreshClick()
                        }
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
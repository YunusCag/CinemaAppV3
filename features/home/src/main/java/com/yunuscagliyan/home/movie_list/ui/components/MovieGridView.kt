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
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.loading.LoadingView
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
    onMovieTap: (MovieModel?) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(column.columnCount),
            contentPadding = PaddingValues(
                //horizontal = if (column == MovieListGridColumn.SINGLE_ITEM) 8.dp else 0.dp
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
        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    val isSingleItem = column == MovieListGridColumn.SINGLE_ITEM
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(column.columnCount),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(if (isSingleItem) 12.dp else 0.dp),
                        horizontalArrangement = Arrangement.spacedBy(if (isSingleItem) 8.dp else 0.dp),
                        contentPadding = PaddingValues(
                            horizontal = 8.dp
                        )
                    ) {
                        items(30) {
                            AnimatedShimmer {
                                Box(
                                    modifier = Modifier
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
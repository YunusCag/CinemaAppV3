package com.yunuscagliyan.home.movie_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core_ui.components.header.SimpleTopBar
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.LIST_TYPE_KEY
import com.yunuscagliyan.core_ui.components.anim.AnimationBox
import com.yunuscagliyan.core_ui.components.ripple.NoRippleInteractionSource
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.home.data.enum.MovieListGridColumn
import com.yunuscagliyan.home.data.enum.MoviePagingType
import com.yunuscagliyan.home.movie_list.ui.components.GenreListRow
import com.yunuscagliyan.home.movie_list.ui.components.MovieGridView
import com.yunuscagliyan.home.movie_list.viewmodel.MovieListViewModel
import timber.log.Timber


object MovieListScreen : CoreScreen<MovieListViewModel>() {
    override val route: String
        get() = RootScreenRoute.MovieList.route

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(
            name = LIST_TYPE_KEY,
        ) {
            type = NavType.IntType
            defaultValue = MoviePagingType.UPCOMING.index
        }
    )

    @Composable
    override fun viewModel(): MovieListViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: MovieListViewModel) {
        val state by viewModel.state
        val movies = viewModel.movies.collectAsLazyPagingItems()
        LaunchedEffect(key1 = state.genreList) {
            Timber.e("Refresh called.")
            movies.refresh()
        }

        MainUIFrame(
            topBar = {
                TopBar(
                    moviePagingType = state.listType,
                    column = state.columnCount,
                    onBackTap = viewModel::popBack,
                    onColumnChange = viewModel::changeColumnCount
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
            ) {
                MovieGridView(
                    modifier = Modifier
                        .fillMaxSize(),
                    movies = movies,
                    column = state.columnCount,
                    onRefreshClick =viewModel::getMovieGenreList,
                    onMovieTap = viewModel::onMovieTap
                )
                AnimationBox {
                    GenreListRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                CinemaAppTheme.colors.background.copy(
                                    alpha = 0.1f
                                )
                            )
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                        genres = state.genreList,
                        selectedIds = state.selectedGenreIds,
                        onSelected = { genre, isSelected ->
                            viewModel.onGenreClick(genre, isSelected)
                        },
                        isLoading = state.isGenreLoading,
                        onAllClicked = viewModel::onAllClick
                    )
                }
            }
        }
    }

    @Composable
    private fun TopBar(
        moviePagingType: MoviePagingType,
        column: MovieListGridColumn,
        onBackTap: () -> Unit,
        onColumnChange: (MovieListGridColumn) -> Unit
    ) {
        SimpleTopBar(
            title = stringResource(
                id = when (moviePagingType) {
                    MoviePagingType.UPCOMING -> R.string.up_coming_movie_text
                    MoviePagingType.TRENDING -> R.string.trending_title_text
                    MoviePagingType.POPULAR -> R.string.popular_title_text
                    MoviePagingType.TOP_RATED -> R.string.top_rated_title_text
                }
            ),
            leftActions = {
                IconButton(
                    onClick = onBackTap,
                    interactionSource = NoRippleInteractionSource()
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.common_back_button_description),
                        modifier = Modifier
                            .size(24.dp),
                        tint = CinemaAppTheme.colors.whiteColor
                    )
                }
            },
            rightActions = {
                IconButton(
                    onClick = {
                        onColumnChange(
                            when (column) {
                                MovieListGridColumn.SINGLE_ITEM -> MovieListGridColumn.COLUMN_TWO
                                MovieListGridColumn.COLUMN_TWO -> MovieListGridColumn.COLUMN_THIRD
                                MovieListGridColumn.COLUMN_THIRD -> MovieListGridColumn.SINGLE_ITEM
                            }
                        )
                    },
                ) {
                    Icon(
                        painter = painterResource(
                            id = when (column) {
                                MovieListGridColumn.COLUMN_THIRD -> R.drawable.ic_grid_column_single
                                MovieListGridColumn.COLUMN_TWO -> R.drawable.ic_grid_column_third
                                MovieListGridColumn.SINGLE_ITEM -> R.drawable.ic_grid_column_two
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        tint = CinemaAppTheme.colors.whiteColor
                    )
                }
            }
        )
    }
}
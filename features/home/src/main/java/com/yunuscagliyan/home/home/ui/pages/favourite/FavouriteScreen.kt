package com.yunuscagliyan.home.home.ui.pages.favourite

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core.data.local.entity.MovieEntity
import com.yunuscagliyan.core.data.mapper.toMovieModel
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.navigation.MainScreenRoute
import com.yunuscagliyan.core.util.Constants.DurationUTil.DISMISS_ANIMATION_DURATION
import com.yunuscagliyan.core_ui.components.card.MovieLargeCard
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.components.loading.LoadingView
import com.yunuscagliyan.core_ui.extension.asString
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.home.home.viewmodel.favourite.FavouriteViewModel

object FavouriteScreen : CoreScreen<FavouriteViewModel>() {
    override val route: String
        get() = MainScreenRoute.Favourites.route

    @Composable
    override fun viewModel(): FavouriteViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: FavouriteViewModel) {
        val state by viewModel.state
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (state.favouriteError != null) {
                    NetworkErrorView(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp
                            ),
                        message = state.favouriteError?.asString()
                    )
                } else if (state.favouriteLoading) {
                    LoadingView(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp
                            ),
                    )
                } else {
                    FavouriteList(
                        favourites = state.favourites,
                        onDismiss = viewModel::onDismiss,
                        onNavigateDetail = viewModel::onMovieTap
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    private fun FavouriteList(
        favourites: List<MovieEntity>,
        onDismiss: (Int, MovieEntity) -> Unit,
        onNavigateDetail: (MovieModel) -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                bottom = 16.dp
            )
        ) {
            items(
                favourites.size,
                key = {
                    val entity = favourites[it]
                    entity.hashCode()
                }
            ) { index ->
                val entity = favourites[index]
                val movie = entity.toMovieModel()
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            onDismiss(index, entity)
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    dismissThresholds = { _ ->
                        FractionalThreshold(0.2f)
                    },
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> CinemaAppTheme.colors.secondary
                                else -> Color.Transparent
                            },
                            animationSpec = tween(DISMISS_ANIMATION_DURATION)
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
                            animationSpec = tween(DISMISS_ANIMATION_DURATION)
                        )

                        Box(
                            Modifier
                                .padding(
                                    top = 24.dp
                                )
                                .fillMaxSize()
                                .background(color)
                                .padding(
                                    horizontal = 16.dp
                                ),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Delete Icon",
                                tint = CinemaAppTheme.colors.whiteColor,
                                modifier = Modifier
                                    .size(36.dp)
                                    .scale(scale)
                            )
                        }
                    },
                ) {
                    MovieLargeCard(
                        model = movie
                    ) {
                        onNavigateDetail(movie)
                    }
                }
            }
        }
    }
}
package com.yunuscagliyan.movie_detail.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.android.gms.ads.AdSize
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.data.remote.model.cast.CastModel
import com.yunuscagliyan.core.data.remote.model.crew.CrewModel
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.MOVIE_ID_KEY
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.admob.BannerAd
import com.yunuscagliyan.core_ui.components.anim.AnimationBox
import com.yunuscagliyan.core_ui.components.button.FavoriteButton
import com.yunuscagliyan.core_ui.components.button.SecondaryMediumTextButton
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.components.header.SimpleTopBar
import com.yunuscagliyan.core_ui.components.list.HorizontalMovieListView
import com.yunuscagliyan.core_ui.components.list.VideoThumbnailListView
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.components.ripple.NoRippleInteractionSource
import com.yunuscagliyan.core_ui.extension.asString
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.movie_detail.ui.components.*
import com.yunuscagliyan.movie_detail.viewmodel.detail.MovieDetailState
import com.yunuscagliyan.movie_detail.viewmodel.detail.MovieDetailViewModel
import java.lang.Float.min
import java.util.*

object MovieDetailScreen : CoreScreen<MovieDetailViewModel>() {
    override val route: String = RootScreenRoute.MovieDetail.route

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(
            name = MOVIE_ID_KEY
        ) {
            type = NavType.IntType
        }
    )

    @Composable
    override fun viewModel(): MovieDetailViewModel = hiltViewModel()

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    override fun Content(viewModel: MovieDetailViewModel) {
        val state by viewModel.state
        val similarMovies = viewModel.similarMovies
        val similarLazy = similarMovies.collectAsLazyPagingItems()

        val scrollState = rememberScrollState()

        val onRefresh: () -> Unit = remember {
            {

                similarLazy.refresh()
                viewModel.initState()
            }
        }

        MainUIFrame() {
            Box {
                if (state.movieDetailError != null) {
                    NetworkErrorView(
                        message = state.movieDetailError?.asString(),
                        onRefreshClick = onRefresh
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(CinemaAppTheme.colors.background)
                            .verticalScroll(scrollState)
                    ) {
                        ParallaxHeader(
                            state = state,
                            scrollValue = {
                                scrollState.value
                            },
                            scrollMaxValue = {
                                scrollState.maxValue
                            },
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        if (state.movieDetailLoading) {
                            TopSectionShimmer()
                        } else {
                            TopSection(
                                state = state
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        if (state.movieDetailLoading) {
                            OverviewShimmer()
                        } else {
                            BannerAd()
                            state.movieDetailResponse?.overview?.let { overview ->
                                Overview(
                                    overview = overview
                                )
                            }
                        }
                        Cast(
                            isLoading = state.castLoading,
                            cast = state.cast,
                            viewModel = viewModel
                        )
                        BannerAd(
                            paddingValues = PaddingValues(),
                            adSize = AdSize.LARGE_BANNER
                        )
                        Crew(
                            isLoading = state.castLoading,
                            crew = state.crew
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalMovieListView(
                            movies = similarMovies,
                            title = stringResource(id = R.string.movie_detail_similar),
                            titleTextStyle = CinemaAppTheme.typography.normalText,
                            titleColor = CinemaAppTheme.colors.secondary,
                            titlePadding = PaddingValues(
                                start = 16.dp,
                                bottom = 8.dp
                            ),
                            listPadding = PaddingValues(
                                start = 16.dp
                            ),
                            onMovieTap = viewModel::onMovieClick
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        if (state.videoLoading || state.videoList.isNotEmpty()) {
                            VideoThumbnailListView(
                                videos = state.videoList,
                                isLoading = state.videoLoading,
                                onVideoClick = viewModel::onVideoClick
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
                TopBar(
                    scrollValue = {
                        scrollState.value
                    },
                    alpha = {
                        if (scrollState.value == 0 || state.movieDetailError != null) 1f
                        else min(
                            1f,
                            (scrollState.value.toFloat() / scrollState.maxValue.toFloat()) * 10
                        )
                    },
                    state = state,
                    onBackPress = viewModel::popBack,
                    onFavouriteClick = viewModel::onFavouriteClick
                )
            }

        }
    }

    @Composable
    private fun TopBar(
        scrollValue: () -> Int,
        alpha: () -> Float,
        state: MovieDetailState,
        onBackPress: () -> Unit,
        onFavouriteClick: (Boolean) -> Unit
    ) {
        val backgroundColor = if (state.movieDetailError != null) {
            CinemaAppTheme.colors.primary
        } else {
            if (scrollValue() == 0) Color.Unspecified else CinemaAppTheme.colors.primary
        }

        SimpleTopBar(
            modifier = Modifier
                .alpha(
                    alpha()
                ),
            title = state.movieDetailResponse?.title ?: EMPTY_STRING,
            backgroundColor = backgroundColor,
            rightActions = {
                if (state.movieDetailError == null && !state.movieDetailLoading) {
                    AnimationBox {
                        FavoriteButton(
                            isFavorite = state.isFavourite,
                            onClick = onFavouriteClick
                        )
                    }
                }
            },
            leftActions = {
                IconButton(
                    onClick = onBackPress,
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
        )
    }

    @Composable
    private fun Cast(
        cast: List<CastModel>,
        isLoading: Boolean,
        viewModel: MovieDetailViewModel
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.movie_detail_cast),
                style = CinemaAppTheme.typography.normalText,
                color = CinemaAppTheme.colors.secondary,
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp
                        )
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    repeat(10) {
                        ProfileShimmer()
                    }
                }
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    items(cast.size) { index ->
                        val castModel = cast[index]
                        ProfileView(
                            profilePath = castModel.profilePath,
                            title = castModel.name,
                            description = castModel.character
                        ) {
                            viewModel.onCastClick(castModel)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Crew(
        crew: List<CrewModel>,
        isLoading: Boolean
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.movie_detail_crew),
                style = CinemaAppTheme.typography.normalText,
                color = CinemaAppTheme.colors.secondary,
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp
                        )
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    repeat(10) {
                        ProfileShimmer()
                    }
                }
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    items(crew.size) { index ->
                        val crewModel = crew[index]
                        ProfileView(
                            profilePath = crewModel.profilePath,
                            title = crewModel.name,
                            description = crewModel.job
                        ) {

                        }
                    }
                }
            }
        }
    }
}
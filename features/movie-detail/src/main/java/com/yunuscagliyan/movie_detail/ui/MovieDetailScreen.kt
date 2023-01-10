package com.yunuscagliyan.movie_detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.data.remote.model.cast.CastModel
import com.yunuscagliyan.core.data.remote.model.country.ProductionCountryModel
import com.yunuscagliyan.core.data.remote.model.crew.CrewModel
import com.yunuscagliyan.core.data.remote.model.genre.GenreModel
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.MOVIE_ID_KEY
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.header.SimpleTopBar
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.list.HorizontalMovieListView
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.components.ripple.NoRippleInteractionSource
import com.yunuscagliyan.core_ui.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.core_ui.extension.formatDate
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.movie_detail.ui.components.*
import com.yunuscagliyan.movie_detail.viewmodel.MovieDetailState
import com.yunuscagliyan.movie_detail.viewmodel.MovieDetailViewModel
import java.lang.Float.min
import java.text.NumberFormat
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


        val scrollState = rememberScrollState()

        MainUIFrame {
            Box {
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
                    Spacer(modifier = Modifier.height(12.dp))
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
                }
                TopBar(
                    scrollValue = {
                        scrollState.value
                    },
                    scrollMaxValue = {
                        scrollState.maxValue
                    },
                    state = state,
                    onBackPress = viewModel::popBack
                )
            }
        }
    }

    @Composable
    private fun TopBar(
        scrollValue: () -> Int,
        scrollMaxValue: () -> Int,
        state: MovieDetailState,
        onBackPress: () -> Unit
    ) {
        SimpleTopBar(
            modifier = Modifier
                .alpha(
                    if (scrollValue() == 0) 1f else min(
                        1f,
                        (scrollValue().toFloat() / scrollMaxValue().toFloat()) * 20
                    )
                ),
            title = state.movieDetailResponse?.title ?: EMPTY_STRING,
            backgroundColor = if (scrollValue() == 0) Color.Unspecified else CinemaAppTheme.colors.primary,
            rightActions = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.common_back_button_description),
                    modifier = Modifier
                        .size(24.dp),
                    tint = Color.Transparent
                )
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
                        start = 16.dp
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
                        start = 16.dp
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
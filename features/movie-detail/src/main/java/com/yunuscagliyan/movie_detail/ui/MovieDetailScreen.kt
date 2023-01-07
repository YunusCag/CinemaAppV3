package com.yunuscagliyan.movie_detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.data.remote.model.country.ProductionCountryModel
import com.yunuscagliyan.core.data.remote.model.genre.GenreModel
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.MOVIE_ID_KEY
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.header.SimpleTopBar
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.components.ripple.NoRippleInteractionSource
import com.yunuscagliyan.core_ui.extension.formatDate
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
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

    @Composable
    override fun Content(viewModel: MovieDetailViewModel) {
        val state by viewModel.state

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
                    TopSection(
                        state = state
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    state.movieDetailResponse?.overview?.let { overview ->
                        Overview(
                            overview = overview
                        )
                    }

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
    private fun ParallaxHeader(
        state: MovieDetailState,
        scrollValue: () -> Int,
        scrollMaxValue: () -> Int,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .graphicsLayer {
                    alpha = 1f - ((scrollValue().toFloat() / scrollMaxValue()) * 1.5f)
                    translationY = 0.5f * scrollValue()
                },
        ) {
            AppImage(
                modifier = Modifier
                    .fillMaxSize(),
                url = state.movieDetailResponse?.backdropPath,
                description = state.movieDetailResponse?.title,
                contentScale = ContentScale.Crop
            )
        }
    }

    @Composable
    private fun TopSection(
        state: MovieDetailState
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(
                    horizontal = 16.dp
                )
        ) {
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .fillMaxHeight(),
                shape = CinemaAppTheme.shapes.medium,
            ) {
                AppImage(
                    modifier = Modifier.fillMaxSize(),
                    url = state.movieDetailResponse?.posterPath,
                    description = state.movieDetailResponse?.title
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = state.movieDetailResponse?.title ?: EMPTY_STRING,
                    style = CinemaAppTheme.typography.subTitle,
                    color = CinemaAppTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = state.movieDetailResponse?.originalTitle ?: EMPTY_STRING,
                    style = CinemaAppTheme.typography.smallText1,
                    color = CinemaAppTheme.colors.secondaryGray
                )
                state.movieDetailResponse?.productionCountries?.let { countries ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Label(
                            label = stringResource(id = R.string.movie_detail_country)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = getCountriesListText(countries),
                            style = CinemaAppTheme.typography.smallText2,
                            color = CinemaAppTheme.colors.textPrimary
                        )
                    }
                }
                state.movieDetailResponse?.genres?.let { genres ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Label(
                            label = stringResource(id = R.string.movie_detail_genre)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = getGenreListText(genres),
                            style = CinemaAppTheme.typography.smallText2,
                            color = CinemaAppTheme.colors.textPrimary
                        )
                    }
                }
                state.movieDetailResponse?.budget?.let { budget ->
                    if (budget > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Label(
                                label = stringResource(id = R.string.movie_detail_budget)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${
                                    NumberFormat.getInstance(Locale.getDefault()).format(budget)
                                } ${
                                    stringResource(
                                        id = R.string.movie_detail_currency
                                    )
                                }",
                                style = CinemaAppTheme.typography.smallText2,
                                color = CinemaAppTheme.colors.textPrimary
                            )
                        }
                    }
                }
                state.movieDetailResponse?.revenue?.let { revenue ->
                    if (revenue > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Label(
                                label = stringResource(id = R.string.movie_detail_revenue)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${
                                    NumberFormat.getInstance(Locale.getDefault()).format(revenue)
                                } ${
                                    stringResource(
                                        id = R.string.movie_detail_currency
                                    )
                                }",
                                style = CinemaAppTheme.typography.smallText2,
                                color = CinemaAppTheme.colors.textPrimary
                            )
                        }
                    }
                }
                state.movieDetailResponse?.releaseDate?.let { releaseDate ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Label(
                            label = stringResource(id = R.string.movie_detail_release_date)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = releaseDate.formatDate() ?: EMPTY_STRING,
                            style = CinemaAppTheme.typography.smallText2,
                            color = CinemaAppTheme.colors.textPrimary
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun Label(
        label: String
    ) {
        Text(
            text = label,
            style = CinemaAppTheme.typography.smallText1,
            color = CinemaAppTheme.colors.secondary
        )
    }

    @Composable
    fun Overview(
        overview: String
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = overview,
            style = CinemaAppTheme.typography.normalText,
            color = CinemaAppTheme.colors.textPrimary,
            textAlign = TextAlign.Justify
        )
    }

    private fun getGenreListText(
        genres: List<GenreModel>?
    ): String {
        var text = EMPTY_STRING
        genres?.forEachIndexed { index, genreModel ->
            text += if (genres.size - 1 == index) {
                genreModel.name ?: EMPTY_STRING
            } else {
                "${genreModel.name ?: EMPTY_STRING}, "
            }
        }
        return text
    }

    private fun getCountriesListText(
        genres: List<ProductionCountryModel>?
    ): String {
        var text = EMPTY_STRING
        genres?.forEachIndexed { index, country ->
            text += if (genres.size - 1 == index) {
                country.name ?: EMPTY_STRING
            } else {
                "${country.name ?: EMPTY_STRING} | "
            }
        }
        return text
    }
}
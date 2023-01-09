package com.yunuscagliyan.movie_detail.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.data.remote.model.country.ProductionCountryModel
import com.yunuscagliyan.core.data.remote.model.genre.GenreModel
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.extension.formatDate
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.movie_detail.viewmodel.MovieDetailState
import java.text.NumberFormat
import java.util.*

@Composable
fun TopSection(
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
                text = state.movieDetailResponse?.title ?: Constants.StringParameter.EMPTY_STRING,
                style = CinemaAppTheme.typography.subTitle,
                color = CinemaAppTheme.colors.textPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = state.movieDetailResponse?.originalTitle ?: Constants.StringParameter.EMPTY_STRING,
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
                        text = releaseDate.formatDate() ?: Constants.StringParameter.EMPTY_STRING,
                        style = CinemaAppTheme.typography.smallText2,
                        color = CinemaAppTheme.colors.textPrimary
                    )
                }
            }
        }
    }
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


private fun getGenreListText(
    genres: List<GenreModel>?
): String {
    var text = Constants.StringParameter.EMPTY_STRING
    genres?.forEachIndexed { index, genreModel ->
        text += if (genres.size - 1 == index) {
            genreModel.name ?: Constants.StringParameter.EMPTY_STRING
        } else {
            "${genreModel.name ?: Constants.StringParameter.EMPTY_STRING}, "
        }
    }
    return text
}

private fun getCountriesListText(
    genres: List<ProductionCountryModel>?
): String {
    var text = Constants.StringParameter.EMPTY_STRING
    genres?.forEachIndexed { index, country ->
        text += if (genres.size - 1 == index) {
            country.name ?: Constants.StringParameter.EMPTY_STRING
        } else {
            "${country.name ?: Constants.StringParameter.EMPTY_STRING} | "
        }
    }
    return text
}
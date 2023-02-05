package com.yunuscagliyan.core_ui.components.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.label.MovieRateLabel
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieLargeCard(
    modifier: Modifier=Modifier,
    model: MovieModel?,
    onTap: () -> Unit
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
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 24.dp),
            shape = CinemaAppTheme.shapes.defaultSmallShape,
            backgroundColor = CinemaAppTheme.colors.card,
            elevation = 4.dp,
            onClick = onTap
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
                    text = model?.title ?: Constants.StringParameter.EMPTY_STRING,
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
                    text = model?.overview ?: Constants.StringParameter.EMPTY_STRING,
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
                modifier = Modifier
                    .fillMaxSize(),
                url = model?.posterPath,
                description = model?.title,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}
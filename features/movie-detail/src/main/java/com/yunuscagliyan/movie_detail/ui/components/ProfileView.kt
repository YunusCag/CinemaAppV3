package com.yunuscagliyan.movie_detail.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme


@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    profilePath: String? = null,
    title: String? = null,
    description: String? = null,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .width(100.dp)
            .noRippleClickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppImage(
            url = profilePath,
            description = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title ?: EMPTY_STRING,
            style = CinemaAppTheme.typography.normalText,
            color = CinemaAppTheme.colors.textPrimary,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = description ?: EMPTY_STRING,
            style = CinemaAppTheme.typography.smallText1,
            color = CinemaAppTheme.colors.secondaryGray,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ProfileShimmer() {
    AnimatedShimmer {
        Column(
            modifier = Modifier
                .width(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(CircleShape)
                    .background(it)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .clip(CinemaAppTheme.shapes.medium)
                    .background(it)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
                    .clip(CinemaAppTheme.shapes.medium)
                    .background(it)
            )
        }
    }
}
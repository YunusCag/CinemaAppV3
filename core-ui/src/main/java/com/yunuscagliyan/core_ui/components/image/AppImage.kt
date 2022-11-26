package com.yunuscagliyan.core_ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.yunuscagliyan.core.BuildConfig
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core_ui.components.error.NetworkErrorView
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    url: String?,
    description: String?,
    contentScale: ContentScale = ContentScale.Crop
) {
    val painter =
        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${BuildConfig.POSTER_BASE_URL}${url ?: EMPTY_STRING}")
                .size(Size.ORIGINAL)
                .build(),
            error = painterResource(id = R.drawable.ic_replay)
        )
    when (painter.state) {
        is AsyncImagePainter.State.Loading -> {
            Box(
                modifier = modifier
                    .background(CinemaAppTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(36.dp),
                    color = CinemaAppTheme.colors.secondary
                )
            }
        }
        is AsyncImagePainter.State.Success -> {
            Image(
                painter = painter,
                contentDescription = description ?: EMPTY_STRING,
                modifier = modifier,
                contentScale = contentScale,
            )
        }
        else -> {
            Image(
                painter = painter,
                contentDescription = description ?: EMPTY_STRING,
                modifier = modifier,
                contentScale = contentScale,
            )
        }
    }
}
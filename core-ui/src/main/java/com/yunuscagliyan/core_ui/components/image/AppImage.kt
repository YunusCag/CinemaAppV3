package com.yunuscagliyan.core_ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.yunuscagliyan.core.BuildConfig
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING

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
            )
    Image(
        painter = painter,
        contentDescription = description ?: EMPTY_STRING,
        modifier = modifier,
        contentScale = contentScale,
    )
}
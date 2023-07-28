package com.yunuscagliyan.core_ui.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.data.remote.model.video.MovieVideoModel
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.image.YoutubeThumbnailImage
import com.yunuscagliyan.core_ui.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme


@Composable
fun VideoThumbnailListView(
    isLoading: Boolean,
    videos: List<MovieVideoModel>,
    onVideoClick: (MovieVideoModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.movie_detail_trailer_title),
            style = CinemaAppTheme.typography.normalText,
            color = CinemaAppTheme.colors.secondary,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                )
        )

        if (isLoading) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(10) {
                    VideoShimmerItem()
                }
            }
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(videos.size) { index ->
                    val video = videos[index]
                    VideoThumbnail(
                        videoKey = video.key,
                        name = video.name
                    ){
                        onVideoClick(video)
                    }
                }
            }
        }
    }
}


@Composable
private fun VideoThumbnail(
    videoKey: String?,
    name: String?,
    onClick:() -> Unit
) {
    Column(
        modifier = Modifier
            .noRippleClickable {
                onClick()
            }
            .width(200.dp)
            .wrapContentHeight(),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            backgroundColor = Color.Unspecified,
            shape = CinemaAppTheme.shapes.defaultSmallShape,
            elevation = 0.dp
        ) {
            Box(
                contentAlignment = Alignment.Center
            ){
                YoutubeThumbnailImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    videoKey = videoKey,
                    description = name,
                    contentScale = ContentScale.Crop
                )

                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = name ?: EMPTY_STRING,
                    modifier = Modifier
                        .size(48.dp),
                    tint = CinemaAppTheme.colors.secondary
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = name ?: EMPTY_STRING,
            style = CinemaAppTheme.typography.smallText1,
            color = CinemaAppTheme.colors.textPrimary,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun VideoShimmerItem() {
    AnimatedShimmer {
        Column(
            modifier = Modifier
                .width(200.dp)
                .wrapContentHeight()
        ) {

            Box(
                modifier = Modifier
                    .background(
                        brush = it,
                        shape = CinemaAppTheme.shapes.defaultSmallShape
                    )
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .background(it, shape = CinemaAppTheme.shapes.defaultSmallShape)
                    .fillMaxWidth()
                    .height(20.dp)
            )
        }
    }

}
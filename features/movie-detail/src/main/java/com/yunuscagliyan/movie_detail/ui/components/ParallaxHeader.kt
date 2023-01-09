package com.yunuscagliyan.movie_detail.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.components.image.AppImage
import com.yunuscagliyan.core_ui.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.movie_detail.viewmodel.MovieDetailState

@Composable
fun ParallaxHeader(
    state: MovieDetailState,
    scrollValue: () -> Int,
    scrollMaxValue: () -> Int,
) {
    if (state.movieDetailLoading) {
        AnimatedShimmer {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(it)
            )

        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .graphicsLayer {
                    alpha = 1f - ((scrollValue().toFloat() / scrollMaxValue()) * 1.2f)
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
}
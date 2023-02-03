package com.yunuscagliyan.movie_detail.viewmodel.video

import androidx.compose.runtime.Stable

@Stable
data class VideoState(
    val videoId: String? = null,
    val videoName: String? = null,
    val isVideoPlaying: Boolean = false
)

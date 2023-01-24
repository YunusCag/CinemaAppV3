package com.yunuscagliyan.movie_detail.viewmodel

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.data.remote.model.cast.CastModel
import com.yunuscagliyan.core.data.remote.model.crew.CrewModel
import com.yunuscagliyan.core.data.remote.model.video.MovieVideoModel
import com.yunuscagliyan.core.data.remote.response.MovieDetailResponse
import com.yunuscagliyan.core.util.UIText

@Stable
data class MovieDetailState(
    val movieDetailLoading: Boolean = true,
    val movieDetailResponse: MovieDetailResponse? = null,
    val movieDetailError: UIText? = null,
    val castLoading: Boolean = true,
    val cast: List<CastModel> = emptyList(),
    val crew: List<CrewModel> = emptyList(),
    val castError: UIText? = null,
    val videoLoading: Boolean = true,
    val videoList: List<MovieVideoModel> = emptyList(),
    val videoError: UIText? = null
)

package com.yunuscagliyan.movie_detail.viewmodel

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.data.remote.response.MovieDetailResponse
import com.yunuscagliyan.core.util.UIText

@Stable
data class MovieDetailState(
    val movieDetailLoading: Boolean = true,
    val movieDetailResponse: MovieDetailResponse? = null,
    val movieDetailError: UIText? = null
)

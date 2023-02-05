package com.yunuscagliyan.home.home.viewmodel.favourite

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.data.local.entity.MovieEntity
import com.yunuscagliyan.core.util.UIText

@Stable
data class FavouriteState(
    val favourites: List<MovieEntity> = emptyList(),
    val favouriteLoading: Boolean = true,
    val favouriteError: UIText? = null,
)

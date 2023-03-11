package com.yunuscagliyan.home.home.viewmodel.main

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.data.enums.ThemeType


@Stable
data class MovieState(
    val currentTheme: ThemeType = ThemeType.Auto
)

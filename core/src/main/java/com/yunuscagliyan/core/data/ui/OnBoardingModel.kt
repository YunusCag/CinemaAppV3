package com.yunuscagliyan.core.data.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingModel(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
)

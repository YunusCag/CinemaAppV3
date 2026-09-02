package com.yunuscagliyan.core.data.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingModel(
    @param:StringRes val title: Int,
    @param:StringRes val description: Int,
    @param:DrawableRes val image: Int,
)

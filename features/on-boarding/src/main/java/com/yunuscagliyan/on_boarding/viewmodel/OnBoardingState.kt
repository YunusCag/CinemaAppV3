package com.yunuscagliyan.on_boarding.viewmodel

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.data.ui.OnBoardingModel

@Stable
data class OnBoardingState(
    val introduceList: List<OnBoardingModel> = emptyList()
)

package com.yunuscagliyan.splash.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.yunuscagliyan.core.data.local.preference.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var state by mutableStateOf(SplashState())
        private set

    init {
        initState()
    }

    private fun initState() {
        state = state.copy(
            shouldShowOnBoarding = preferences.shouldShowOnBoard
        )
    }
}
package com.yunuscagliyan.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.navigation.Screen
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.navigation.RouteNavigationOptions
import com.yunuscagliyan.core_ui.navigation.Routes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferences: Preferences
) : CoreViewModel() {

    init {
        initState()
    }

    private fun initState() {
        val shouldShow = preferences.shouldShowOnBoard

        viewModelScope.launch {
            delay(4000L)
            sendEvent(
                CoreEvent.Navigation(
                    Routes.NavigateToRoute(
                        if (shouldShow) Screen.OnBoarding.route else Screen.Home.route,
                        options = RouteNavigationOptions(
                            Screen.Splash.route,
                            inclusive = true
                        )
                    )
                )
            )
        }
    }
}
package com.yunuscagliyan.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants.DurationUTil.SPLASH_DURATION
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

     fun initState() {
        val shouldShow = preferences.shouldShowOnBoard

        viewModelScope.launch {
            delay(SPLASH_DURATION.toLong())
            navigateNext(shouldShow)
        }
    }

    private fun navigateNext(shouldShow: Boolean) {
        sendEvent(
            CoreEvent.Navigation(
                Routes.NavigateToRoute(
                    if (shouldShow) RootScreenRoute.OnBoarding.route else RootScreenRoute.Main.route,
                    options = RouteNavigationOptions(
                        RootScreenRoute.Splash.route,
                        inclusive = true
                    )
                )
            )
        )
    }
}
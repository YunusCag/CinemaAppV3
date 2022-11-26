package com.yunuscagliyan.on_boarding.viewmodel

import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.navigation.Routes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val preferences: Preferences
) : CoreViewModel() {
    fun saveShowShouldPreference() {
        preferences.shouldShowOnBoard = false
        sendEvent(
            CoreEvent.Navigation(
                Routes.NavigateToRoute(
                    RootScreenRoute.Main.route
                )
            )
        )
    }
}
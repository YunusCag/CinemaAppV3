package com.yunuscagliyan.core_ui.navigation

import kotlinx.coroutines.flow.StateFlow

interface RouteNavigator {
    fun onNavigated(state: Routes)
    fun navigateUp()
    fun popToRoute(route: String)
    fun navigateToRoute(route: String)

    val navigationState: StateFlow<Routes>
}


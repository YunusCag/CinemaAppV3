package com.yunuscagliyan.core_ui.navigation


sealed class Routes {
    data class NavigateToRoute(
        val pageRoute: String,
        val options: RouteNavigationOptions? = null
    ) : Routes()

    data class PopBackRoute(
        val route: String,
        val inclusive: Boolean = false
    ) : Routes()

    object PopBack : Routes()
}

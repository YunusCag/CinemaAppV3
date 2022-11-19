package com.yunuscagliyan.core_ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import kotlinx.coroutines.flow.collectLatest

interface CoreScreen<T : CoreViewModel> {
    val route: String


    fun getArguments(): List<NamedNavArgument> = emptyList()
    fun getDeepLinks(): List<NavDeepLink> = emptyList()

    @Composable
    fun viewModel(): T

    @Composable
    fun Content(viewModel: T)

    fun composable(
        builder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        builder.composable(route, getArguments(), getDeepLinks()) {
            val viewModel = viewModel()

            LaunchedEffect(Unit) {
                viewModel.uiEvent.collectLatest { event ->
                    when (event) {
                        is CoreEvent.Navigation -> {
                            handleNavigation(
                                navHostController,
                                event.state
                            )
                        }
                    }
                }
            }
            Content(viewModel)
        }
    }

    private fun handleNavigation(
        navHostController: NavHostController,
        routes: Routes,
    ) {
        when (routes) {
            is Routes.NavigateToRoute -> {
                navHostController.navigate(
                    routes.pageRoute,
                    navOptions = routes.options?.getNavOptions()
                )
            }
            is Routes.PopBackRoute -> {
                navHostController.popBackStack(
                    routes.route,
                    routes.inclusive
                )
            }
            is Routes.PopBack -> {
                navHostController.popBackStack()
            }
        }
    }
}
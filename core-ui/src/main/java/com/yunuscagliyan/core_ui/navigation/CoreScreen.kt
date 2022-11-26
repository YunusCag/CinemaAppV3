package com.yunuscagliyan.core_ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import kotlinx.coroutines.flow.collectLatest


abstract class CoreScreen<T : CoreViewModel> {
    var navController: NavHostController? = null
    abstract val route: String


    open fun getArguments(): List<NamedNavArgument> = emptyList()
    open fun getDeepLinks(): List<NavDeepLink> = emptyList()

    @Composable
    abstract fun viewModel(): T

    @Composable
    abstract fun Content(viewModel: T)

    @OptIn(ExperimentalAnimationApi::class)
    fun composable(
        builder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        this.navController = navHostController

        builder.composable(
            route,
            getArguments(),
            getDeepLinks(),
        ) {
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
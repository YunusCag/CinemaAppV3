package com.yunuscagliyan.core_ui.navigation

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.extension.asString
import com.yunuscagliyan.core_ui.extension.showInterstitial
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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
            val snackState = remember { SnackbarHostState() }
            val snackScope = rememberCoroutineScope()
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                viewModel.uiEvent.collectLatest { event ->
                    when (event) {
                        is CoreEvent.Navigation -> {
                            context.showInterstitial {
                                handleNavigation(
                                    navHostController,
                                    event.state
                                )
                            }
                        }
                        is CoreEvent.ShowSnackBar -> {
                            snackScope.launch {
                                val result = snackState.showSnackbar(
                                    message = event.message.asString(context),
                                    actionLabel = event.actionLabel?.asString(context),
                                    duration = event.duration
                                )
                                when (result) {
                                    SnackbarResult.Dismissed -> Unit
                                    SnackbarResult.ActionPerformed -> {
                                        event.onClick?.invoke()
                                    }
                                }
                            }
                        }
                        is CoreEvent.Toast -> {
                            Toast.makeText(
                                context,
                                event.message.asString(context),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }


            Content(viewModel)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                SnackbarHost(
                    hostState = snackState,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    snackbar = {
                        Snackbar(
                            snackbarData = it,
                            actionColor = CinemaAppTheme.colors.secondary,
                            backgroundColor = CinemaAppTheme.colors.primaryDark,
                            contentColor = CinemaAppTheme.colors.whiteColor,
                            shape = CinemaAppTheme.shapes.defaultMediumShape
                        )
                    }
                )
            }
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
                    routes.inclusive,
                )
            }
            is Routes.PopBack -> {
                navHostController.popBackStack()
            }
        }
    }
}
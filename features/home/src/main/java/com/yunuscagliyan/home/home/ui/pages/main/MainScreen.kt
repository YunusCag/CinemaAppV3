package com.yunuscagliyan.home.home.ui.pages.main

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.navigation.MainScreenRoute
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core_ui.components.header.SimpleTopBar
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.components.ripple.NoRippleInteractionSource
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.home.home.ui.pages.favourite.FavouriteScreen
import com.yunuscagliyan.home.home.ui.pages.home.HomeScreen
import com.yunuscagliyan.home.home.ui.pages.settings.SettingsScreen
import com.yunuscagliyan.home.home.viewmodel.main.MainViewModel

object MainScreen : CoreScreen<MainViewModel>() {
    override val route: String
        get() = RootScreenRoute.Main.route

    @Composable
    override fun viewModel(): MainViewModel = hiltViewModel()

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun Content(viewModel: MainViewModel) {
        val bottomBarNavController = rememberAnimatedNavController()
        MainUIFrame(
            topBar = {
                TopBar(
                    bottomBarNavController
                )
            },
            bottomBar = {
                BottomBar(bottomBarNavController)
            }
        ) { paddingValues ->
            this.navController?.let {
                Box(
                    modifier = Modifier
                        .padding(paddingValues),
                ) {
                    SetUpNavGraph(
                        bottomBarNavController = bottomBarNavController,
                        it,
                    )

                }
            }
        }
    }

    @Composable
    private fun TopBar(navController: NavHostController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        SimpleTopBar(
            title = stringResource(
                id = when (currentDestination?.route) {
                    MainScreenRoute.Home.route -> R.string.home_page_top_bar_title
                    MainScreenRoute.Favourites.route -> R.string.favourite_page_top_bar_title
                    MainScreenRoute.Settings.route -> R.string.settings_page_top_bar_title
                    else -> R.string.home_page_top_bar_title
                }
            ),
        )
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun SetUpNavGraph(
        bottomBarNavController: NavHostController,
        rootNavHostController: NavHostController
    ) {
        AnimatedNavHost(
            navController = bottomBarNavController,
            startDestination = MainScreenRoute.Home.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        Constants.DurationUTil.TRANSITION_DURATION,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        Constants.DurationUTil.TRANSITION_DURATION,
                        easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(
                        Constants.DurationUTil.TRANSITION_DURATION,
                        easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(
                        Constants.DurationUTil.TRANSITION_DURATION,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            HomeScreen.composable(
                this,
                rootNavHostController
            )
            FavouriteScreen.composable(
                this,
                rootNavHostController
            )
            SettingsScreen.composable(
                this,
                rootNavHostController
            )
        }
    }

    @Composable
    private fun BottomBar(navController: NavHostController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        BottomNavigation(
            backgroundColor = CinemaAppTheme.colors.background,
        ) {
            BottomBarItem(
                label = R.string.home_page_top_bar_title,
                icon = Icons.Default.Home,
                route = MainScreenRoute.Home.route,
                navController = navController,
                currentDestination = currentDestination
            )
            BottomBarItem(
                label = R.string.favourite_page_top_bar_title,
                icon = Icons.Default.Favorite,
                route = MainScreenRoute.Favourites.route,
                navController = navController,
                currentDestination = currentDestination
            )
            BottomBarItem(
                label = R.string.settings_page_top_bar_title,
                icon = Icons.Default.Settings,
                route = MainScreenRoute.Settings.route,
                navController = navController,
                currentDestination = currentDestination
            )
        }

    }

    @Composable
    fun RowScope.BottomBarItem(
        @StringRes label: Int,
        icon: ImageVector,
        route: String,
        navController: NavHostController,
        currentDestination: NavDestination?,
    ) {
        val selected = currentDestination?.hierarchy?.any {
            it.route == route
        } == true
        BottomNavigationItem(
            label = {
                Text(
                    text = stringResource(id = label),
                    style = CinemaAppTheme.typography.smallText1,
                )
            },
            icon = {
                Icon(
                    icon,
                    modifier = Modifier
                        .size(24.dp),
                    contentDescription = null
                )
            },
            selected = selected,
            selectedContentColor = CinemaAppTheme.colors.secondary,
            unselectedContentColor = CinemaAppTheme.colors.textPrimary,
            interactionSource = NoRippleInteractionSource(),
            onClick = {
                if(!selected){
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}
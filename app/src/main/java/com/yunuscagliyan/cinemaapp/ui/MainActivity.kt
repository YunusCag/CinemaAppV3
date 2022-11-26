package com.yunuscagliyan.cinemaapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.yunuscagliyan.cinemaapp.navigation.SetupNavGraph
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinemaAppTheme {
                val navController = rememberAnimatedNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
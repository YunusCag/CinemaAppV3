package com.yunuscagliyan.cinemaapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.yunuscagliyan.cinemaapp.navigation.SetupNavGraph
import com.yunuscagliyan.core.helper.LanguageHelper
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.home.home.viewmodel.main.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(50)
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }

        setContent {
            val context= LocalContext.current
            val movieViewModel:MovieViewModel= hiltViewModel()
            movieViewModel.initLocale(context)

            CinemaAppTheme {
                val navController = rememberAnimatedNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
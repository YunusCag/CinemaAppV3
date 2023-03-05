package com.yunuscagliyan.splash.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants.DurationUTil.SPLASH_DURATION
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.splash.viewmodel.SplashViewModel
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.util.Constants.DurationUTil.LOW_ANIMATION_DURATION
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object SplashScreen : CoreScreen<SplashViewModel>() {
    override val route: String
        get() = RootScreenRoute.Splash.route

    @Composable
    override fun viewModel(): SplashViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: SplashViewModel) {
        var startAnimation by remember { mutableStateOf(false) }
        val coroutine = rememberCoroutineScope()


        val alphaAnim = animateFloatAsState(
            targetValue = if (startAnimation) 1f else 0f,
            animationSpec = tween(
                durationMillis = SPLASH_DURATION,
                delayMillis = LOW_ANIMATION_DURATION
            )
        )

        LaunchedEffect(key1 = true) {
            startAnimation = true
            coroutine.launch {
                delay(SPLASH_DURATION.toLong())
                viewModel.initState()
            }
        }

        AnimatedSplash(alphaAnim.value)
    }

    @Composable
    private fun AnimatedSplash(alpha: Float) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CinemaAppTheme.colors.primary),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alpha = alpha),
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = null
            )
        }
    }
}
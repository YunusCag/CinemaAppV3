package com.yunuscagliyan.splash.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core.navigation.Screen
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.splash.viewmodel.SplashViewModel


object SplashScreen:CoreScreen<SplashViewModel>{
    override val route: String
        get() = Screen.Splash.route

    @Composable
    override fun viewModel(): SplashViewModel= hiltViewModel()

    @Composable
    override fun Content(viewModel: SplashViewModel) {
        var startAnimation by remember { mutableStateOf(false) }

        val alphaAnim = animateFloatAsState(
            targetValue = if (startAnimation) 1f else 0f,
            animationSpec = tween(
                delayMillis = 3000
            )
        )

        LaunchedEffect(key1 = true) {
            startAnimation = true
        }

        AnimatedSplash(alphaAnim.value)
    }

    @Composable
    private fun AnimatedSplash(alpha: Float) {
        Box(
            modifier = Modifier
                .background(if (isSystemInDarkTheme()) Color.Black else Color.Blue)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alpha = alpha),
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
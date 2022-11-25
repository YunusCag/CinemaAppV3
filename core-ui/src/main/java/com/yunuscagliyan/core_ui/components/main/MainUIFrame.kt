package com.yunuscagliyan.core_ui.components.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme


@Composable
fun MainUIFrame(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    backgroundColor: Brush = Brush.horizontalGradient(
        listOf(
            CinemaAppTheme.colors.primary,
            CinemaAppTheme.colors.primaryDark,
        )
    ),
    content: @Composable (PaddingValues) -> Unit,
) {

    CinemaAppTheme{
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding(),
            topBar = topBar,
            backgroundColor = CinemaAppTheme.colors.background,

        ) { paddingValues ->
            content(paddingValues)
        }
    }
}
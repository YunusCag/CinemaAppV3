package com.yunuscagliyan.core_ui.components.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Green
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme


@Composable
fun MainUIFrame(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    backgroundColor: Brush = Brush.horizontalGradient(
        listOf(
            CinemaAppTheme.colors.primary,
            CinemaAppTheme.colors.primaryDark,
        )
    ),
    content: @Composable (PaddingValues) -> Unit,
) {

    CinemaAppTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding(),
            topBar = topBar,
            bottomBar = bottomBar,
            backgroundColor = CinemaAppTheme.colors.background,
            snackbarHost = {
                SnackbarHost(it) { data ->
                    Snackbar(
                        actionColor = CinemaAppTheme.colors.secondary,
                        snackbarData = data
                    )
                }
            }
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}
package com.yunuscagliyan.core_ui.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme


@Composable
fun MainUIFrame(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    statusBarColor: Color = CinemaAppTheme.colors.primary,
    navigationBarColor: Color = CinemaAppTheme.colors.background,
    backgroundColor: Brush = Brush.horizontalGradient(
        listOf(
            CinemaAppTheme.colors.primary,
            CinemaAppTheme.colors.primaryDark,
        )
    ),
    content: @Composable (PaddingValues) -> Unit,
) {
    // Apps must draw edge to edge from Android 15 on, and the system bar colors can no
    // longer be set from code, so the bar areas are painted by the app itself here and
    // the scaffold is kept between them.
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(statusBarColor)
        )
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier.weight(1f),
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
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .background(navigationBarColor)
        )
    }
}

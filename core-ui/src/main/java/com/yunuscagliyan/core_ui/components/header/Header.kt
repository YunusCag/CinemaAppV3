package com.yunuscagliyan.core_ui.components.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun BaseTopBar(
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    backgroundColor: Color = CinemaAppTheme.colors.primary,
    title: (@Composable RowScope.() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier,
        elevation = elevation,
        backgroundColor = backgroundColor
    ) {
        if (title != null) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = title,
            )
        }
    }
}

@Composable
fun SimpleTopBar(
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    backgroundColor: Color = CinemaAppTheme.colors.primary,
    title: String,
) {
    BaseTopBar(
        modifier = modifier,
        elevation = elevation,
        backgroundColor = backgroundColor,
        title = {
            Text(
                text = title,
                style = CinemaAppTheme.typography.headline,
                color = CinemaAppTheme.colors.whiteColor
            )
        }
    )
}
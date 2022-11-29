package com.yunuscagliyan.core_ui.components.header

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun BaseTopBar(
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    backgroundColor: Color = CinemaAppTheme.colors.primary,
    title: (@Composable RowScope.() -> Unit)? = null,
    leftActions: (@Composable RowScope.() -> Unit)? = null,
    rightActions: (@Composable RowScope.() -> Unit)? = null,
    leftSpace: Dp = 12.dp,
    rightSpace: Dp = 12.dp
) {
    TopAppBar(
        modifier = modifier,
        elevation = elevation,
        backgroundColor = backgroundColor,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            if (leftActions != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(leftSpace),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 8.dp),
                    content = leftActions,
                )
            }
            if (title != null) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = title,
                )
            }
            if (rightActions != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(rightSpace),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 8.dp),
                    content = rightActions,
                )
            }
        }
    }
}

@Composable
fun SimpleTopBar(
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    backgroundColor: Color = CinemaAppTheme.colors.primary,
    title: String,
    leftActions: (@Composable RowScope.() -> Unit)? = null,
    rightActions: (@Composable RowScope.() -> Unit)? = null,
) {
    BaseTopBar(
        modifier = modifier,
        elevation = elevation,
        backgroundColor = backgroundColor,
        title = {
            Text(
                text = title,
                style = CinemaAppTheme.typography.headline2,
                color = CinemaAppTheme.colors.whiteColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leftActions = leftActions,
        rightActions = rightActions
    )
}
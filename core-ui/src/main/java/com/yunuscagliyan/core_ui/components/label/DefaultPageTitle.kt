package com.yunuscagliyan.core_ui.components.label

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun DefaultPageTitle(
    title: String,
    style: TextStyle = CinemaAppTheme.typography.subTitle,
    color: Color = CinemaAppTheme.colors.textPrimary
) {
    Text(
        text = title,
        style = style,
        color = color
    )
}
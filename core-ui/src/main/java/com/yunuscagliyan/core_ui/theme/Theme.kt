package com.yunuscagliyan.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = EbonyClay,
    primaryVariant = Firefly,
    secondary = Amaranth,
)

private val LightColorPalette = lightColors(
    primary = EbonyClay,
    primaryVariant = Firefly,
    secondary = Amaranth,
)

@Composable
fun CinemaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}
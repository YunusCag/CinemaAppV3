package com.yunuscagliyan.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object CinemaAppTheme {
    val colors: CinemaAppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val shapes: CinemaAppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current

    val typography: CinemaAppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current
}


private val LocalAppShapes = staticCompositionLocalOf {
    defaultAppShapes()
}

private val LocalAppTypography = staticCompositionLocalOf {
    defaultAppTypography()
}

private val LocalAppColors = staticCompositionLocalOf<CinemaAppColors> {
    error("CinemaAppColorPalette was not ")
}

private val DarkColorPalette = CinemaAppColors(
    primary = AppCustomDarkColors.colorPrimary,
    primaryDark = AppCustomDarkColors.colorPrimaryDark,
    secondary = AppCustomDarkColors.colorSecondary,
    accent = AppCustomDarkColors.colorAccent,
    whiteColor = AppCustomDarkColors.colorWhite,
    blackColor = AppCustomDarkColors.colorBlack,
    background = AppCustomDarkColors.colorBackground,
    textPrimary = AppCustomDarkColors.colorTextPrimary,
    secondaryGray = AppCustomDarkColors.colorSecondaryGray,
    card = AppCustomDarkColors.colorCard,
    isDark = true
)

private val LightColorPalette = CinemaAppColors(
    primary = AppCustomLightColors.colorPrimary,
    primaryDark = AppCustomLightColors.colorPrimaryDark,
    secondary = AppCustomLightColors.colorSecondary,
    accent = AppCustomLightColors.colorAccent,
    whiteColor = AppCustomLightColors.colorWhite,
    blackColor = AppCustomLightColors.colorBlack,
    background = AppCustomLightColors.colorBackground,
    textPrimary = AppCustomLightColors.colorTextPrimary,
    secondaryGray = AppCustomLightColors.colorSecondaryGray,
    card = AppCustomLightColors.colorCard,
    isDark = false
)

@Composable
fun CinemaAppTheme(
    typography: CinemaAppTypography = CinemaAppTheme.typography,
    shapes: CinemaAppShapes = CinemaAppTheme.shapes,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = colors.primaryDark,
        darkIcons = false
    )
    systemUiController.setNavigationBarColor(
        color = colors.background,
        darkIcons = !darkTheme
    )

    val rememberedColors = remember { colors.copy() }.apply { update(colors) }
    CompositionLocalProvider(
        LocalAppColors provides rememberedColors,
        LocalAppShapes provides shapes,
        LocalAppTypography provides typography,
        content = content
    )

}

@Composable
fun ProvideCinemaAppColors(
    colors: CinemaAppColors,
    typography: CinemaAppTypography = appTypography(),
    shapes: CinemaAppShapes = appShapes(),
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(
        LocalAppColors provides colorPalette,
        LocalAppShapes provides shapes,
        LocalAppTypography provides typography,
        content = content
    )
}
package com.yunuscagliyan.core_ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
class CinemaAppColors(
    whiteColor: Color,
    blackColor: Color,
    primary: Color,
    primaryDark: Color,
    secondary: Color,
    accent: Color,
    background: Color,
    textPrimary: Color,
    isDark: Boolean
) {
    var whiteColor by mutableStateOf(whiteColor)
        private set
    var blackColor by mutableStateOf(blackColor)
        private set
    var primary by mutableStateOf(primary)
        private set
    var primaryDark by mutableStateOf(primaryDark)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var accent by mutableStateOf(accent)
        private set
    var background by mutableStateOf(background)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun copy(): CinemaAppColors = CinemaAppColors(
        whiteColor = whiteColor,
        blackColor = blackColor,
        primary = primary,
        primaryDark = primaryDark,
        secondary = secondary,
        accent = accent,
        background = background,
        textPrimary = textPrimary,
        isDark = isDark
    )

    fun update(other: CinemaAppColors) {
        whiteColor = other.whiteColor
        blackColor = other.blackColor
        primary = other.primary
        primaryDark = other.primaryDark
        secondary = other.secondary
        accent = other.accent
        background = other.background
        textPrimary = other.textPrimary
        isDark = other.isDark
    }
}

private interface AppColor {
    val colorPrimary: Color
    val colorPrimaryDark: Color
    val colorSecondary: Color
    val colorAccent: Color
    val colorBackground: Color
    val colorBlack: Color
    val colorWhite: Color
    val colorTextPrimary: Color

}

object AppCustomLightColors : AppColor {

    override val colorPrimary = Color(0xFF0A1823)
    override val colorPrimaryDark = Color(0xFF0A1823)
    override val colorSecondary = Color(0xFFE82251)
    override val colorAccent = Color(0xFFFBC02D)
    override val colorBackground = Color(0xFFFFFFFF)

    override val colorBlack = Color(0xFF000000)
    override val colorWhite = Color(0xFFFFFFFF)
    override val colorTextPrimary: Color = Color(0xFF000000)
}

object AppCustomDarkColors : AppColor {
    override val colorPrimary = Color(0xFF0A1823)
    override val colorPrimaryDark = Color(0xFF0A1823)
    override val colorSecondary = Color(0xFFE82251)
    override val colorAccent = Color(0xFFFBC02D)
    override val colorBackground = Color(0xFF161E29)

    override val colorBlack = Color(0xFF000000)
    override val colorWhite = Color(0xFFFFFFFF)
    override val colorTextPrimary: Color = Color(0xFFFFFFFF)
}

val EbonyClay = Color(0xFF273343)
val Firefly = Color(0xFF0A1823)
val Amaranth = Color(0xFFE82251)
val Gunmetal = Color(0xFF273343)

val yellow300 = Color(0xFFFFF176)
val yellow500 = Color(0xFFFFEB3B)
val yellow700 = Color(0xFFFBC02D)
val yellow900 = Color(0xFFF57F17)
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
    secondaryGray: Color,
    card: Color,
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
    var secondaryGray by mutableStateOf(secondaryGray)
        private set
    var card by mutableStateOf(card)
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
        secondaryGray = secondaryGray,
        card = card,
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
        secondaryGray = other.secondaryGray
        card = other.card
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
    val colorSecondaryGray: Color
    val colorCard: Color

}

object AppCustomLightColors : AppColor {
    override val colorPrimary = Color(0xFF0A1823)
    override val colorPrimaryDark = Color(0xFF0A1823)
    override val colorSecondary = Color(0xFFE82251)
    override val colorAccent = Color(0xFFFBC02D)
    override val colorBackground = Color(0xFFF5F5F5)

    override val colorBlack = Color(0xFF000000)
    override val colorWhite = Color(0xFFFFFFFF)
    override val colorTextPrimary: Color = Color(0xFF000000)
    override val colorSecondaryGray: Color = Color(0xFF8294A8)
    override val colorCard: Color = Color(0xFFFFFFFF)
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
    override val colorSecondaryGray: Color = Color(0xFF8294A8)
    override val colorCard: Color = Color(0xFF273343)
}
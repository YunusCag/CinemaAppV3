package com.yunuscagliyan.core_ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yunuscagliyan.core.R

data class CinemaAppTypography(
    val materialTypography: Typography
) {
    val headline: TextStyle
        @Composable
        get() {
            return TextStyle(
                fontFamily = CinemaAppFontFamily.OpenSans,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 32.sp,
            )
        }

    val title: TextStyle
        @Composable
        get() {
            return TextStyle(
                fontFamily = CinemaAppFontFamily.OpenSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 26.sp,
            )
        }
    val subTitle: TextStyle
        @Composable
        get() {
            return TextStyle(
                fontFamily = CinemaAppFontFamily.OpenSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            )
        }

    val normalText: TextStyle
        @Composable
        get() {
            return TextStyle(
                fontFamily = CinemaAppFontFamily.OpenSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 22.sp,
            )
        }

    val smallText1: TextStyle
        @Composable
        get() {
            return TextStyle(
                fontFamily = CinemaAppFontFamily.OpenSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            )
        }
    val smallText2: TextStyle
        @Composable
        get() {
            return TextStyle(
                fontFamily = CinemaAppFontFamily.OpenSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                lineHeight = 16.sp,
            )
        }
}

fun defaultAppTypography() = appTypography()

fun appTypography(): CinemaAppTypography {
    val baseTextStyle = TextStyle(
        fontFamily = CinemaAppFontFamily.OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 22.sp,
        letterSpacing = (0.3f).sp
    )
    return CinemaAppTypography(
        materialTypography = Typography(
            body1 = baseTextStyle
        )
    )
}

@Immutable
object CinemaAppFontFamily {
    @Stable
    val OpenSans = FontFamily(
        Font(
            R.font.open_sans_regular
        ),
        Font(
            R.font.open_sans_semi_bold,
            weight = FontWeight.SemiBold
        ),
        Font(
            R.font.open_sans_bold,
            weight = FontWeight.Bold
        )
    )
}
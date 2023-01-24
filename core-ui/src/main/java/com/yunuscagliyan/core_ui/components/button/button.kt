package com.yunuscagliyan.core_ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme


@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    buttonSize: Dp = 42.dp,
    enabled: Boolean = true,
    textColor: Color = CinemaAppTheme.colors.textPrimary,
    buttonShape: Shape = CinemaAppTheme.shapes.defaultSmallShape,
    buttonPaddingValues: PaddingValues = PaddingValues(),
    backgroundColor: Color = CinemaAppTheme.colors.secondary,
    backgroundBrush: Brush? = null,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = textColor,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = textColor.copy(alpha = 0.5f)
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        shape = buttonShape,
        contentPadding = buttonPaddingValues,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundBrush ?: SolidColor(backgroundColor)),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        },
        modifier = modifier
            .heightIn(min = buttonSize,)
            .height(IntrinsicSize.Max)
            .width(intrinsicSize = IntrinsicSize.Max)
            .alpha(if (enabled) 1f else 0.5f)
    )
}


@Composable
fun SecondaryMediumTextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    buttonSize: Dp = 42.dp,
    textStyle: TextStyle = CinemaAppTheme.typography.normalText,
    textColor: Color = CinemaAppTheme.colors.textPrimary,
    onClick: () -> Unit
) {
    BaseButton(
        modifier = modifier,
        enabled = enabled,
        textColor = textColor,
        buttonSize = buttonSize,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = textStyle,
                color = textColor
            )
        }
    }
}
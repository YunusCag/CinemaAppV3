package com.yunuscagliyan.core_ui.components.toggle

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.util.Constants.DurationUTil.DEFAULT_ANIMATION_DURATION
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme


@Preview
@Composable
private fun ToggleButtonSelectedPreview() {
    CinemaAppTheme {
        ToggleButton(
            label = "Aksiyon",
            isSelected = true
        ) {

        }
    }
}

@Preview
@Composable
private fun ToggleButtonPreview() {
    CinemaAppTheme {
        ToggleButton(
            label = "Aksiyon",
            isSelected = false
        ) {

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    label: String,
    labelTextStyle: TextStyle = CinemaAppTheme.typography.normalText,
    color: Color = CinemaAppTheme.colors.secondary,
    isSelected: Boolean = false,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 12.dp
    ),
    onClick: (Boolean) -> Unit
) {
    val backgroundColorAnim = animateColorAsState(
        targetValue = if (isSelected)
            color else
            Color.Unspecified,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION)
    )

    val strokeColorAnim = animateColorAsState(
        targetValue = if (isSelected)
            Color.Unspecified
        else
            color,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION)
    )

    val textColorAnim = animateColorAsState(
        targetValue = if (isSelected)
            CinemaAppTheme.colors.whiteColor else
            color,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION)
    )

    Card(
        modifier = modifier,
        shape = CinemaAppTheme.shapes.defaultLargeShape,
        backgroundColor = backgroundColorAnim.value,
        elevation = 0.dp,
        border = BorderStroke(
            width = 2.dp,
            color = strokeColorAnim.value
        ),
        onClick = {
            onClick(isSelected)
        }
    ) {
        Row(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Text(
                text = label,
                style = labelTextStyle,
                color = textColorAnim.value,
                textAlign = TextAlign.Center
            )
        }
    }
}
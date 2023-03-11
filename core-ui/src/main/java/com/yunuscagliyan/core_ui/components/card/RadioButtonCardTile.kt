package com.yunuscagliyan.core_ui.components.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RadioButtonCardTile(
    modifier: Modifier = Modifier,
    title: String,
    shape: Shape = CinemaAppTheme.shapes.defaultLargeShape,
    backgroundColor: Color = CinemaAppTheme.colors.card,
    textStyle: TextStyle = CinemaAppTheme.typography.normalText,
    textColor: Color = CinemaAppTheme.colors.textPrimary,
    checked: Boolean = false,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = shape,
        backgroundColor = backgroundColor,
        elevation = 4.dp,
        onClick = {
            onCheckedChange(!checked)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = checked,
                enabled = enabled,
                onClick = { onCheckedChange(!checked) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = CinemaAppTheme.colors.secondary,
                    unselectedColor = CinemaAppTheme.colors.secondaryGray
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = textStyle,
                color = textColor
            )
        }
    }
}
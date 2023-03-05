package com.yunuscagliyan.core_ui.components.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Preview
@Composable
fun CheckboxCardTileCheckedPreview() {
    CinemaAppTheme {
        CheckboxCardTile(
            title = "Deneme Yazısı",
            checked = true,
            onCheckedChange = {

            }
        )
    }
}

@Preview
@Composable
fun CheckboxCardTilePreview() {
    CinemaAppTheme {
        CheckboxCardTile(
            title = "Deneme Yazısı",
            onCheckedChange = {

            }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckboxCardTile(
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
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                colors = CheckboxDefaults.colors(
                    checkedColor = CinemaAppTheme.colors.secondary,
                    uncheckedColor = CinemaAppTheme.colors.secondaryGray,
                    checkmarkColor = CinemaAppTheme.colors.whiteColor,
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
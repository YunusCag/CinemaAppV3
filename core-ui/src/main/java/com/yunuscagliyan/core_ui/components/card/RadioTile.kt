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
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun RadioTile(
    modifier: Modifier = Modifier,
    title: String,
    textStyle: TextStyle = CinemaAppTheme.typography.normalText,
    textColor: Color = CinemaAppTheme.colors.textPrimary,
    checked: Boolean = false,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable {
                onCheckedChange(!checked)
            }
            .padding(
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

@Composable
fun RadioTileList(
    modifier: Modifier = Modifier,
    itemList: List<String>,
    selectedIndex: Int,
    shape: Shape = CinemaAppTheme.shapes.defaultLargeShape,
    backgroundColor: Color = CinemaAppTheme.colors.card,
    textStyle: TextStyle = CinemaAppTheme.typography.normalText,
    textColor: Color = CinemaAppTheme.colors.textPrimary,
    onCheckedChange: (Int, Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = shape,
        backgroundColor = backgroundColor,
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                )
        ) {
            repeat(itemList.size) { index ->
                val title = itemList[index]
                RadioTile(
                    modifier = Modifier
                        .fillMaxWidth(),
                    checked = selectedIndex == index,
                    title = title,
                    textStyle = textStyle,
                    textColor = textColor,
                    onCheckedChange = {
                        onCheckedChange(index, it)
                    }
                )
                if (index != itemList.size - 1) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = CinemaAppTheme.colors.dividerColor
                    )
                }
            }
        }
    }
}
package com.yunuscagliyan.core_ui.components.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    selectedColor: Color = CinemaAppTheme.colors.secondary,
    unSelectedColor: Color = CinemaAppTheme.colors.whiteColor,
    onClick: (Boolean) -> Unit,
) {

    val colorAnim = animateColorAsState(
        targetValue = if (isFavorite)
            selectedColor
        else
            unSelectedColor,
        animationSpec = tween(Constants.DurationUTil.DEFAULT_ANIMATION_DURATION)
    )

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            onClick(!isFavorite)
        }
    ) {
        Icon(
            tint = colorAnim.value,
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}
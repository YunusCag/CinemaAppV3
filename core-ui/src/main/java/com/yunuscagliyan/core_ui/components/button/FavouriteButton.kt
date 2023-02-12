package com.yunuscagliyan.core_ui.components.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    size: Dp = 36.dp,
    selectedColor: Color = CinemaAppTheme.colors.secondary,
    unSelectedColor: Color = CinemaAppTheme.colors.whiteColor,
    onClick: (Boolean) -> Unit,
) {
    val state = remember {
        MutableTransitionState(isFavorite)
    }

    val colorAnim = animateColorAsState(
        targetValue = if (state.targetState)
            selectedColor
        else
            unSelectedColor,
        animationSpec = tween(Constants.DurationUTil.DEFAULT_ANIMATION_DURATION)
    )


    val scale = animateFloatAsState(
        targetValue = if (state.targetState) 1f else 0.9f,
        animationSpec = tween(Constants.DurationUTil.DEFAULT_ANIMATION_DURATION)
    )

    IconToggleButton(
        checked = state.currentState,
        onCheckedChange = {
            state.targetState = !isFavorite
            onClick(!isFavorite)
        }
    ) {
        Icon(
            tint = colorAnim.value,
            modifier = modifier
                .size(size)
                .scale(scale.value),
            imageVector = if (state.targetState) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}
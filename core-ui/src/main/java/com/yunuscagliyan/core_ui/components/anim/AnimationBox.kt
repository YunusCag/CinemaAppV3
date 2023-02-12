package com.yunuscagliyan.core_ui.components.anim

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.yunuscagliyan.core.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimationBox(
    modifier: Modifier=Modifier,
    enter: EnterTransition = expandVertically() + fadeIn(),
    exit: ExitTransition = fadeOut() + shrinkVertically(),
    duration: Long = Constants.DurationUTil.ANIMATION_BOX_DURATION.toLong(),
    content: @Composable () -> Unit
) {
    val state = remember {
        MutableTransitionState(false)
    }

    val coroutine = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        coroutine.launch {
            delay(duration)
            state.targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = enter,
        exit = exit
    ) { content() }
}
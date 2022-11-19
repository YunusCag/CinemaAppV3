package com.yunuscagliyan.core_ui.components.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush


@Composable
fun MainUIFrame(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    backgroundColor: Brush? = null,
    content: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        modifier = modifier.statusBarsPadding(),
        topBar = topBar,
    ) { paddingValues ->
        content(paddingValues)
    }
}
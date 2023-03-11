package com.yunuscagliyan.home.home.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.yunuscagliyan.home.home.viewmodel.main.AppViewModel

@Composable
fun appViewModel(): AppViewModel =
    androidx.lifecycle.viewmodel.compose.viewModel(LocalContext.current as ComponentActivity)
package com.yunuscagliyan.home.home.ui.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import com.yunuscagliyan.home.home.viewmodel.main.AppViewModel

@Composable
fun appViewModel(): AppViewModel =
    androidx.lifecycle.viewmodel.compose.viewModel(LocalActivity.current as ComponentActivity)

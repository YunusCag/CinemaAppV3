package com.yunuscagliyan.cinemaapp.presentation.screens.home.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Text("Home Page")
        },
    )
}
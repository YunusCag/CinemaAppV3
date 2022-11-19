package com.yunuscagliyan.home.ui.pages.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core.navigation.Screen
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.home.viewmodel.main.MainViewModel

object MainScreen : CoreScreen<MainViewModel> {
    override val route: String
        get() = Screen.Home.route

    @Composable
    override fun viewModel(): MainViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: MainViewModel) {
        MainUIFrame() {

        }
    }
}
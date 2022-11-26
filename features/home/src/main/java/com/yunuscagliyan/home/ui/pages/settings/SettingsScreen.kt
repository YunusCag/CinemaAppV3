package com.yunuscagliyan.home.ui.pages.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core.navigation.MainScreenRoute
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.home.viewmodel.settings.SettingsViewModel

object SettingsScreen : CoreScreen<SettingsViewModel>() {
    override val route: String
        get() = MainScreenRoute.Settings.route

    @Composable
    override fun viewModel(): SettingsViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: SettingsViewModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
        ) {

        }
    }
}
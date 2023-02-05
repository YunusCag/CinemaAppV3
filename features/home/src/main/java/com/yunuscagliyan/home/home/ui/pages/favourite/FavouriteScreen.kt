package com.yunuscagliyan.home.home.ui.pages.favourite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core.navigation.MainScreenRoute
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.home.home.viewmodel.favourite.FavouriteViewModel

object FavouriteScreen : CoreScreen<FavouriteViewModel>() {
    override val route: String
        get() = MainScreenRoute.Favourites.route

    @Composable
    override fun viewModel(): FavouriteViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: FavouriteViewModel) {
        val state by viewModel.state
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

        }
    }
}
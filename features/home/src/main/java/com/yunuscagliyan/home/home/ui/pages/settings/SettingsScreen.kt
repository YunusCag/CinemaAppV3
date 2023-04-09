package com.yunuscagliyan.home.home.ui.pages.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.enums.RegionType
import com.yunuscagliyan.core.data.enums.ThemeType
import com.yunuscagliyan.core.navigation.MainScreenRoute
import com.yunuscagliyan.core_ui.components.card.RadioTile
import com.yunuscagliyan.core_ui.components.card.RadioTileList
import com.yunuscagliyan.core_ui.components.label.DefaultPageTitle
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.home.home.ui.components.appViewModel
import com.yunuscagliyan.home.home.viewmodel.main.AppViewModel
import com.yunuscagliyan.home.home.viewmodel.settings.SettingsViewModel

object SettingsScreen : CoreScreen<SettingsViewModel>() {
    override val route: String
        get() = MainScreenRoute.Settings.route

    @Composable
    override fun viewModel(): SettingsViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: SettingsViewModel) {
        val appViewModel: AppViewModel = appViewModel()
        val state by viewModel.state
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 16.dp
                )
        ) {
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            LanguageChoice(
                selectedType = state.selectedLanguage,
                onSelectLanguage = {
                    viewModel.changeSelectedLanguage(it, context)
                }
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            RegionChoice(
                selectedType = state.selectedRegion,
                onSelectRegion = {
                    viewModel.changeSelectedRegion(it, context)
                }
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            ThemeChoice(
                selectedType = state.selectedTheme,
                onSelectTheme = {
                    appViewModel.changeTheme(it)
                    viewModel.changeTheme(it)
                }
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }


    @Composable
    private fun LanguageChoice(
        selectedType: LanguageType,
        onSelectLanguage: (LanguageType) -> Unit
    ) {
        val languages = LanguageType.values()
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DefaultPageTitle(
                title = stringResource(id = R.string.settings_language_selection_title)
            )
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            RadioTileList(
                modifier = Modifier
                    .fillMaxWidth(),
                itemList = languages.map { stringResource(id = it.text) }.toList(),
                selectedIndex = selectedType.ordinal,
                onCheckedChange = { index, checked ->
                    LanguageType.fromIndex(index)?.let {
                        onSelectLanguage(it)
                    }
                }
            )
        }

    }

    @Composable
    private fun RegionChoice(
        selectedType: RegionType,
        onSelectRegion: (RegionType) -> Unit
    ) {
        val regions = RegionType.values()

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DefaultPageTitle(
                title = stringResource(id = R.string.settings_region_selection_title)
            )
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            RadioTileList(
                modifier = Modifier
                    .fillMaxWidth(),
                itemList = regions.map { stringResource(id = it.text) }.toList(),
                selectedIndex = selectedType.ordinal,
                onCheckedChange = { index, checked ->
                    RegionType.fromIndex(index)?.let {
                        onSelectRegion(it)
                    }
                }
            )
        }
    }

    @Composable
    private fun ThemeChoice(
        selectedType: ThemeType,
        onSelectTheme: (ThemeType) -> Unit
    ) {
        val themeTypes = ThemeType.values()

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DefaultPageTitle(
                title = stringResource(id = R.string.settings_theme_selection_title)
            )
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            RadioTileList(
                modifier = Modifier
                    .fillMaxWidth(),
                itemList = themeTypes.map { stringResource(id = it.text) }.toList(),
                selectedIndex = selectedType.ordinal,
                onCheckedChange = { index, checked ->
                    ThemeType.fromIndex(index)?.let {
                        onSelectTheme(it)
                    }
                }
            )
        }
    }
}
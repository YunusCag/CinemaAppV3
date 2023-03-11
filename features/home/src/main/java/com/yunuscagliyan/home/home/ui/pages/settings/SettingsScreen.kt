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
import com.yunuscagliyan.core.navigation.MainScreenRoute
import com.yunuscagliyan.core_ui.components.card.CheckboxCardTile
import com.yunuscagliyan.core_ui.components.label.DefaultPageTitle
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.home.home.viewmodel.settings.SettingsViewModel

object SettingsScreen : CoreScreen<SettingsViewModel>() {
    override val route: String
        get() = MainScreenRoute.Settings.route

    @Composable
    override fun viewModel(): SettingsViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: SettingsViewModel) {
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
            repeat(languages.size) { index ->
                val type = languages[index]
                CheckboxCardTile(
                    modifier = Modifier
                        .padding(
                            top = 8.dp
                        ),
                    title = stringResource(id = type.text),
                    checked = selectedType == type,
                    onCheckedChange = {
                        onSelectLanguage(type)
                    }
                )
            }
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
            repeat(regions.size) { index ->
                val type = regions[index]
                CheckboxCardTile(
                    modifier = Modifier
                        .padding(
                            top = 8.dp
                        ),
                    title = stringResource(id = type.text),
                    checked = selectedType == type,
                    onCheckedChange = {
                        onSelectRegion(type)
                    }
                )
            }
        }
    }
}
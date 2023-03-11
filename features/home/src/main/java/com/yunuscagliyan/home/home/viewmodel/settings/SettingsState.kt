package com.yunuscagliyan.home.home.viewmodel.settings

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.enums.RegionType
import com.yunuscagliyan.core.data.enums.ThemeType


@Stable
data class SettingsState(
    val selectedLanguage: LanguageType = LanguageType.TR,
    val selectedRegion: RegionType = RegionType.USA,
    val selectedTheme: ThemeType = ThemeType.Auto
)

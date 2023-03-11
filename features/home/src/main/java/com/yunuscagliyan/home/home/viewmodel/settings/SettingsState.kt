package com.yunuscagliyan.home.home.viewmodel.settings

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.enums.RegionType


@Stable
data class SettingsState(
    val selectedLanguage: LanguageType = LanguageType.TR,
    val selectedRegion: RegionType = RegionType.USA
)

package com.yunuscagliyan.home.home.viewmodel.settings

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.data.enums.LanguageType


@Stable
data class SettingsState(
    val selectedLanguage: LanguageType = LanguageType.TR
)

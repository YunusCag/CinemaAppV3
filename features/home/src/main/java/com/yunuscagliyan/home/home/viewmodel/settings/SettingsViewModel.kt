package com.yunuscagliyan.home.home.viewmodel.settings

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.helper.LanguageHelper
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val languageHelper: LanguageHelper
) : CoreViewModel() {
    val state = mutableStateOf(SettingsState())


    init {
        initState()
    }

    private fun initState() {
        setState(state) {
            val type = languageHelper.getCurrentLanguage()
            copy(
                selectedLanguage = type
            )
        }
    }

    fun changeSelectedLanguage(type: LanguageType, context: Context) {
        if (state.value.selectedLanguage == type) {
            return
        }

        setState(state) {
            copy(
                selectedLanguage = type
            )
        }
        languageHelper.changeLanguage(
            type = type,
            context = context,
            restart = true
        )

    }
}
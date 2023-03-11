package com.yunuscagliyan.home.home.viewmodel.main

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.yunuscagliyan.core.data.enums.ThemeType
import com.yunuscagliyan.core.helper.LanguageHelper
import com.yunuscagliyan.core.helper.ThemeHelper
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val languageHelper: LanguageHelper,
    private val themeHelper: ThemeHelper
) : CoreViewModel() {

    val state = mutableStateOf(MovieState())

    init {
        setState(state) {
            copy(
                currentTheme = themeHelper.themeType
            )
        }
    }

    fun initLocale(context: Context) {
        languageHelper.initLocale(context)
    }

    fun changeTheme(type: ThemeType) {
        setState(state) {
            copy(
                currentTheme = type
            )
        }
    }
}


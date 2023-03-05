package com.yunuscagliyan.home.home.viewmodel.main

import android.content.Context
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.helper.LanguageHelper
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val languageHelper: LanguageHelper,
    private val preferences: Preferences
) : CoreViewModel() {
    fun initLocale(context: Context) {
        languageHelper.initLocale(context)
    }
}


package com.yunuscagliyan.core.helper

import com.yunuscagliyan.core.data.enums.ThemeType
import com.yunuscagliyan.core.data.local.preference.Preferences
import javax.inject.Inject

class ThemeHelper @Inject constructor(
    private val preferences: Preferences
) {
    var themeType: ThemeType = ThemeType.Auto

    init {
        initTheme()
    }

    private fun initTheme() {
        val code = preferences.themeCode
        ThemeType.fromCode(code)?.let {
            themeType = it
        }
    }

    fun changeTheme(type: ThemeType) {
        this.themeType = type
        preferences.themeCode = type.code
    }
}
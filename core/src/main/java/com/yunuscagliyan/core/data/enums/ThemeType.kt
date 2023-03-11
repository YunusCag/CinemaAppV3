package com.yunuscagliyan.core.data.enums

import androidx.annotation.StringRes
import com.yunuscagliyan.core.R

enum class ThemeType(
    val code: String,
    @StringRes val text: Int,
) {
    Auto("auto", R.string.common_system_theme),
    Dark("dark", R.string.common_dark_theme),
    Light("light", R.string.common_light_theme);

    companion object {
        fun fromCode(code: String) = values().find { it.code == code }
    }
}
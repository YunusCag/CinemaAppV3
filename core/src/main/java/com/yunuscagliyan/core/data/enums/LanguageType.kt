package com.yunuscagliyan.core.data.enums

import androidx.annotation.StringRes
import com.yunuscagliyan.core.R

enum class LanguageType(
    val code: String,
    @StringRes val text: Int
) {
    TR("tr", R.string.common_turkish),
    EN("en", R.string.common_english);


    companion object {
        fun fromCode(code: String?): LanguageType? = values().find { it.code == code }
        fun fromIndex(index: Int): LanguageType? = values().find { it.ordinal == index }
    }
}
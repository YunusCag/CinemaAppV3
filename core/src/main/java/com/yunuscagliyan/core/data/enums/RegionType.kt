package com.yunuscagliyan.core.data.enums

import androidx.annotation.StringRes
import com.yunuscagliyan.core.R

enum class RegionType(
    val code: String,
    @StringRes val text: Int,
) {
    TR("TR", R.string.common_turkey),
    USA("US", R.string.common_usa);

    companion object {
        fun fromCode(code: String?): RegionType? = values().find { it.code == code }
    }
}
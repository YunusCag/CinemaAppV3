package com.yunuscagliyan.core.util

import androidx.annotation.StringRes

sealed class UIText {
    data class DynamicString(val value: String) : UIText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Array<Any> = emptyArray()
    ) : UIText()

}

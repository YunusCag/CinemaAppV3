package com.yunuscagliyan.core_ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yunuscagliyan.core.util.UIText

@Composable
fun UIText.asString(): String {
    return when (this) {
        is UIText.DynamicString -> value
        is UIText.StringResource -> stringResource(id = resId, *args)
    }
}
package com.yunuscagliyan.core_ui.components.error

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yunuscagliyan.core.R

@Composable
fun NetworkErrorView(
    message: String?
) {
    Column {
        Text(
            text = message ?: stringResource(id = R.string.common_http_error),
            style = MaterialTheme.typography.h6,
        )
    }
}
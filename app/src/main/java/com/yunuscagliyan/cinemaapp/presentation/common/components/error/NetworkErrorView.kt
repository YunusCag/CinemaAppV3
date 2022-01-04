package com.yunuscagliyan.cinemaapp.presentation.common.components.error

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yunuscagliyan.cinemaapp.R

@Composable
fun NetworkErrorView(
    message: String? = stringResource(id = R.string.common_unknown_error_message),
) {
    Column {
        Text(
            text = message?:stringResource(id = R.string.common_unknown_error_message),
            style = MaterialTheme.typography.h6,
        )
    }
}
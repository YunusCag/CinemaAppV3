package com.yunuscagliyan.core_ui.components.error

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core_ui.components.button.SecondarySmallTextButton
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun NetworkErrorView(
    modifier: Modifier=Modifier,
    message: String?,
    onRefreshClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = message ?: stringResource(id = R.string.common_http_error),
            modifier = Modifier
                .size(48.dp),
            tint = CinemaAppTheme.colors.secondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message ?: stringResource(id = R.string.common_http_error),
            style = CinemaAppTheme.typography.normalText,
            color = CinemaAppTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        onRefreshClick?.let { onClick->
            SecondarySmallTextButton(
                modifier = Modifier
                    .width(160.dp),
                text = stringResource(id = R.string.common_refresh),
                onClick = onClick
            )
        }
    }
}
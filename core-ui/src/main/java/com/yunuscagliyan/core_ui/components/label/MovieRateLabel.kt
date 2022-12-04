package com.yunuscagliyan.core_ui.components.label

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.util.Constants.DoubleFormatterUtil.DECIMAL_ONE
import com.yunuscagliyan.core.util.Constants.StringParameter.RATE_LABEL
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun MovieRateLabel(
    voteAverage: Double?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(CinemaAppTheme.colors.accent.copy(alpha = 0.8f), shape = CinemaAppTheme.shapes.defaultSmallShape)
            .padding(horizontal = 4.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = CinemaAppTheme.colors.whiteColor,
                        fontWeight = CinemaAppTheme.typography.normalText.fontWeight,
                        fontSize = CinemaAppTheme.typography.normalText.fontSize
                    )
                ) {
                    append(DECIMAL_ONE.format(voteAverage ?: 0))

                }
                withStyle(
                    style = SpanStyle(
                        color = CinemaAppTheme.colors.whiteColor,
                        fontWeight = CinemaAppTheme.typography.smallText2.fontWeight,
                        fontSize = CinemaAppTheme.typography.smallText2.fontSize
                    )
                ) {
                    append(RATE_LABEL)
                }
            },
            modifier = Modifier.padding(
                horizontal = 4.dp,
                vertical = 2.dp,
            )
        )
    }
}
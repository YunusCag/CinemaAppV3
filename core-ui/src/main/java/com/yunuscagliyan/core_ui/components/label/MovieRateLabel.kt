package com.yunuscagliyan.core_ui.components.label

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunuscagliyan.core.util.Constants.DoubleFormatterUtil.DECIMAL_ONE
import com.yunuscagliyan.core.util.Constants.StringParameter.RATE_LABEL
import com.yunuscagliyan.core_ui.theme.yellow700

@Composable
fun MovieRateLabel(
    voteAverage: Double?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(yellow700.copy(alpha = 0.8f), shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                ) {
                    append(DECIMAL_ONE.format(voteAverage ?: 0))

                }
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
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
package com.yunuscagliyan.cinemaapp.presentation.common.components.label

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
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
import com.yunuscagliyan.cinemaapp.ui.theme.*

@Composable
fun MovieRateLabel(
    voteAverage: Double?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(Yellow_500.copy(alpha = 0.8f), shape = RoundedCornerShape(4.dp)),
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
                    append("${voteAverage ?: 0}")

                }
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    )
                ) {
                    append("/10")
                }
            },
            modifier = Modifier.padding(
                horizontal = 4.dp,
                vertical = 2.dp,
            )
        )
    }
}
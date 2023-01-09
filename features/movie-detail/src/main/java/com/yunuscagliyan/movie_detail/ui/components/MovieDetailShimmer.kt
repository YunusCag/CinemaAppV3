package com.yunuscagliyan.movie_detail.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.components.shimmer.AnimatedShimmer
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Composable
fun TopSectionShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            )
    ) {
        AnimatedShimmer { shimmerBrush ->
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .background(shimmerBrush, shape = CinemaAppTheme.shapes.medium),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
            ) {
                repeat(5) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(shimmerBrush, shape = CinemaAppTheme.shapes.small),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

}

@Composable
fun OverviewShimmer() {
    AnimatedShimmer { shimmerBrush ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 16.dp)
                .background(shimmerBrush, shape = CinemaAppTheme.shapes.small),
        )
    }
}
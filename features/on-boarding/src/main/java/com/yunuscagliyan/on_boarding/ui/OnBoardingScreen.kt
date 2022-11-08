package com.yunuscagliyan.on_boarding.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunuscagliyan.on_boarding.viewmodel.OnBoardingViewModel


@Composable
fun OnBoardingPage(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    onNavigate: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .clickable {
                    viewModel.saveShowShouldPreference()
                    onNavigate()
                },
            imageVector = Icons.Default.Email,
            contentDescription = null,
            tint = Color.White
        )
    }

}
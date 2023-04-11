package com.yunuscagliyan.core_ui.components.introduce

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme

@Preview(
    showBackground = true
)
@Composable
fun IntroducePagePreview() {
    CinemaAppTheme {
        IntroducePage(
            title = "Film Dünyasına Hoş Geldiniz!",
            description = "Sinema tutkunları için özel olarak tasarlanmış bu uygulamaya hoş geldiniz! En sevdiğiniz filmleri takip etmek, yeni filmleri keşfetmek ve güncel haberlere ulaşmak için buradasınız. Hemen giriş yapın ve film dünyasının keyfini çıkarmaya başlayın!",
            image = R.drawable.introduce_page_first
        )
    }
}


@Composable
fun IntroducePage(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    @DrawableRes image: Int,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = image),
            contentDescription = title,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = CinemaAppTheme.typography.title,
            color = CinemaAppTheme.colors.secondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = description,
            style = CinemaAppTheme.typography.smallText1,
            color = CinemaAppTheme.colors.textPrimary,
            textAlign = TextAlign.Center
        )
    }
}
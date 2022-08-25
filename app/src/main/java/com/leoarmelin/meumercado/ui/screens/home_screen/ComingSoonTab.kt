package com.leoarmelin.meumercado.ui.screens.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.leoarmelin.meumercado.R

@Composable
fun ComingSoonTab() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.money_loading))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .align(Alignment.Center)
                .size(150.dp)
        )
    }
}
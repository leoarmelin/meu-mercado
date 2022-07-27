package com.leoarmelin.meumercado.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingDialog(isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x4D000000))
        ) {
            Text(text = "Loading", color = Color.White, modifier = Modifier.align(Alignment.Center))
        }
    }
}
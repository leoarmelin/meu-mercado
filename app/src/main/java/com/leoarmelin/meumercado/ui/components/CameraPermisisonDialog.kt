package com.leoarmelin.meumercado.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.leoarmelin.meumercado.ui.theme.Gray900
import com.leoarmelin.meumercado.ui.theme.Primary500

@Composable
fun CameraPermissionDialog(isVisible: Boolean, onAccept: () -> Unit) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Bem-vindo ao NFC-e Scanner",
                        style = MaterialTheme.typography.h5,
                        color = Primary500,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            text = {
                Text(
                    text = "Aqui vai conter a explicação de como funciona o aplicativo. Primeiro colocar como que mira a camera no QR Code. Que precisa ser QR code do mercado e que com isso você pode comparar preços e organizar financeiramente",
                    style = MaterialTheme.typography.body1,
                    color = Gray900
                )
            },
            confirmButton = {
                Button(
                    onClick = onAccept,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    elevation = null
                ) {
                    Text(
                        text = "Entendi",
                        style = MaterialTheme.typography.h5,
                        color = Primary500
                    )
                }
            }
        )
    }
}
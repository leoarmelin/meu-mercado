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
                    text = "Nosso aplicativo usa sua câmera para coletar os dados presentes no website que o QRCode leva. Dessa forma, é necessário permitir que o app acesse sua câmera durante o uso do aplicativo para que você desfrute de nossas funcionalidades!",
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
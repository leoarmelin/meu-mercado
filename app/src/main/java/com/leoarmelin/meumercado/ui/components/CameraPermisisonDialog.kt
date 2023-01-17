package com.leoarmelin.meumercado.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.leoarmelin.meumercado.ui.theme.Gray900
import com.leoarmelin.meumercado.ui.theme.Primary500
import com.leoarmelin.meumercado.R
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CameraPermissionDialog(isVisible: Boolean, onAccept: () -> Unit) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        AlertDialog(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            onDismissRequest = {},
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.bem_vindo_ao_precinho),
                        style = MaterialTheme.typography.h5,
                        color = Primary500,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            text = {
                Text(
                    text = stringResource(R.string.nosso_aplicativo_usa_sua_camera),
                    style = MaterialTheme.typography.body1,
                    color = Gray900,
                    lineHeight = 18.sp
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
                        text = stringResource(R.string.entendi),
                        style = MaterialTheme.typography.h5,
                        color = Primary500
                    )
                }
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MeuMercadoTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CameraPermissionDialog(isVisible = true, onAccept = {})
        }
    }
}
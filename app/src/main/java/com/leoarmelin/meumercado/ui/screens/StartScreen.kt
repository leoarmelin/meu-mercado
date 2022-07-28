package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.R
import com.leoarmelin.meumercado.extensions.getActivity
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.theme.Primary800
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.meumercado.ui.theme.Secondary800
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import kotlinx.coroutines.launch

@androidx.camera.core.ExperimentalGetImage
@Composable
fun StartScreen(
    navigationViewModel: NavigationViewModel,
    isCameraPermissionGranted: Boolean,
) {
    val context = LocalContext.current
    val activity = context.getActivity()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Secondary50),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Comece organizando\nsua vida financeira",
            color = Primary800,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = "Ícone de uma nota fiscal",
            tint = Primary800
        )

        Button(
            modifier = Modifier
                .width(200.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Secondary800,
                contentColor = Color.White
            ),
            onClick = {
                scope.launch {
                    if (!isCameraPermissionGranted) {
                        activity?.permissionsHandler?.requestCameraPermission()
                    } else {
                        navigationViewModel.setRoute(NavDestination.Camera.routeName)
                    }
                }
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "Ícone de câmera fotográfica",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Abrir câmera", style = MaterialTheme.typography.h5)
        }
    }
}
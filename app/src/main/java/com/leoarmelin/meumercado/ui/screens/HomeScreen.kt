package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.leoarmelin.meumercado.extensions.getActivity
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import kotlinx.coroutines.launch

@androidx.camera.core.ExperimentalGetImage
@Composable
fun HomeScreen(
    navigationViewModel: NavigationViewModel,
    isCameraPermissionGranted: Boolean,
) {
    val context = LocalContext.current
    val activity = context.getActivity()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Open Camera", modifier = Modifier.clickable {
            scope.launch {
                if (!isCameraPermissionGranted) {
                    activity?.permissionsHandler?.requestCameraPermission()
                } else {
                    navigationViewModel.setRoute(NavDestination.Camera.routeName)
                }
            }
        })
    }
}
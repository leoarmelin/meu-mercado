package com.leoarmelin.meumercado.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.ExperimentalPagerApi
import com.leoarmelin.meumercado.extensions.getActivity
import com.leoarmelin.meumercado.ui.components.BottomCamera
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.navigation.NavDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun HomeScreen(mainViewModel: MainViewModel, navigationViewModel: NavigationViewModel) {
    val context = LocalContext.current
    val activity = context.getActivity()
    val scope = rememberCoroutineScope()
    val isCameraPermissionGranted by mainViewModel.isCameraPermissionGranted.collectAsState()

    BottomCamera(
        totalPrice = null,
        modifier = Modifier
    ) {
        scope.launch {
            scope.launch {
                if (!isCameraPermissionGranted) {
                    activity?.permissionsHandler?.requestCameraPermission()
                } else {
                    navigationViewModel.setRoute(NavDestination.Camera)
                }
            }
        }
    }
}
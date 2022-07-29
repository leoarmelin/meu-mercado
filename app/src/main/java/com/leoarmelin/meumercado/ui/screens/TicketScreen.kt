package com.leoarmelin.meumercado.ui.screens

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.ExperimentalPagerApi
import com.leoarmelin.meumercado.extensions.getActivity
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.components.BottomCamera
import com.leoarmelin.meumercado.ui.components.ProductList
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalGetImage
@Composable
fun TicketScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    isCameraPermissionGranted: Boolean,
) {
    val context = LocalContext.current
    val activity = context.getActivity()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Secondary50)
    ) {
        mainViewModel.ticket?.let { ProductList(it) }

        BottomCamera(
            totalPrice = (mainViewModel.ticket?.price_total ?: 0.0).toMoney(),
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            scope.launch {
                scope.launch {
                    if (!isCameraPermissionGranted) {
                        activity?.permissionsHandler?.requestCameraPermission()
                    } else {
                        navigationViewModel.setRoute(NavDestination.Camera.routeName)
                    }
                }
            }
        }
    }
}
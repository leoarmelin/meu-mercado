package com.leoarmelin.meumercado.ui.screens.home_screen

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.leoarmelin.meumercado.extensions.getActivity
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.components.BottomCamera
import com.leoarmelin.meumercado.ui.components.HomeTabs
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalGetImage
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    isCameraPermissionGranted: Boolean,
) {
    val context = LocalContext.current
    val activity = context.getActivity()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(1)
    val tabsList = listOf("Em Breve", "Meus Tickets")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Secondary50)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeTabs(state = pagerState, tabsList = tabsList) { tabIndex ->
                scope.launch {
                    pagerState.animateScrollToPage(tabIndex)
                }
            }

            HorizontalPager(
                count = tabsList.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> ComingSoonTab()
                    1 -> MyTicketsTab(mainViewModel.userSavedTickets)
                }
            }
        }

        BottomCamera(totalPrice = null, modifier = Modifier.align(Alignment.BottomStart)) {
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
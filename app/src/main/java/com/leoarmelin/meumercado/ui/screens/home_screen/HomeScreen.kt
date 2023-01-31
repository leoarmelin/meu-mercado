package com.leoarmelin.meumercado.ui.screens.home_screen

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.leoarmelin.meumercado.R
import com.leoarmelin.meumercado.extensions.getActivity
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.components.BottomCamera
import com.leoarmelin.meumercado.ui.components.HomeTabs
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalGetImage
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
) {
    val context = LocalContext.current
    val activity = context.getActivity()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(1)
    val tabsList = listOf(stringResource(R.string.em_breve), stringResource(R.string.meus_tickets))
    val isCameraPermissionGranted by mainViewModel.isCameraPermissionGranted.collectAsState()
    val ticketsList by mainViewModel.ticketsList.collectAsState()

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
                    1 -> MyTicketsTab(ticketsList) { ticket ->
                        mainViewModel.setTicket(ticket)
                        navigationViewModel.setRoute(NavDestination.Ticket.route)
                    }
                }
            }
        }

        BottomCamera(totalPrice = null, modifier = Modifier.align(Alignment.BottomStart)) {
            scope.launch {
                scope.launch {
                    if (!isCameraPermissionGranted) {
                        activity?.permissionsHandler?.requestCameraPermission()
                    } else {
                        navigationViewModel.setRoute(NavDestination.Camera.route)
                    }
                }
            }
        }
    }
}
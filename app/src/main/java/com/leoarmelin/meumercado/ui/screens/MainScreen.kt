package com.leoarmelin.meumercado.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.ui.navigation.AppNavHost
import com.leoarmelin.meumercado.ui.navigation.SheetNavHost
import com.leoarmelin.meumercado.ui.theme.CreamOne
import com.leoarmelin.meumercado.ui.theme.White
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.navigation.NavDestination
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@androidx.camera.core.ExperimentalGetImage
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel
) {
    val sheetState = rememberBottomSheetScaffoldState()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val halfScreenHeight = remember { (screenHeight / 2f).dp }
    var isExpanded by remember { mutableStateOf(true) }

    val peekHeight by animateDpAsState(
        targetValue = if (isExpanded) halfScreenHeight else 0.dp,
        label = "peekHeight"
    )

    LaunchedEffect(navigationViewModel.currentRoute) {
        isExpanded = false
        if (navigationViewModel.currentRoute == NavDestination.Camera) return@LaunchedEffect
        delay(1000)
        isExpanded = true
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        sheetContent = {
            SheetNavHost(
                mainViewModel = mainViewModel,
                currentRoute = navigationViewModel.currentRoute
            )
        },
        sheetPeekHeight = peekHeight,
        sheetBackgroundColor = CreamOne,
        backgroundColor = White,
    ) {
        AppNavHost(
            sheetState = sheetState,
            mainViewModel = mainViewModel,
            navigationViewModel = navigationViewModel,
            padding = it
        )
    }
}
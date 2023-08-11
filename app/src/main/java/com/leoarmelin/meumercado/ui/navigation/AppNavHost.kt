package com.leoarmelin.meumercado.ui.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.leoarmelin.meumercado.ui.screens.CameraScreen
import com.leoarmelin.meumercado.ui.screens.HomeScreen
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.api.Result
import com.leoarmelin.sharedmodels.navigation.NavDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@androidx.camera.core.ExperimentalGetImage
@Composable
fun AppNavHost(
    sheetState: BottomSheetScaffoldState,
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    padding: PaddingValues
) {
    val navController = rememberAnimatedNavController()

    val nfceState by mainViewModel.nfceState.collectAsState()

    AppBackHandler(
        currentRoute = navigationViewModel.currentRoute,
        nfceState = nfceState,
        onShowSnackBar = { sheetState.snackbarHostState.showSnackbar("Pressione novamente para fechar.") },
        onPopBack = navigationViewModel::popBack
    )

    NavHost(
        navController = navController,
        startDestination = NavDestination.Home.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {

        composable(
            NavDestination.Camera.route
        ) {
            CameraScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel
            )
        }

        composable(NavDestination.Home.route) {
            HomeScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel
            )
        }
    }

    LaunchedEffect(navigationViewModel.currentRoute) {
        navController.navigate(navigationViewModel.currentRoute.route)
    }
}

@Composable
private fun AppBackHandler(
    currentRoute: NavDestination,
    nfceState: Result<String>?,
    onShowSnackBar: suspend () -> Unit,
    onPopBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as? Activity
    var closeCount by remember { mutableStateOf(0) }

    BackHandler {
        when (currentRoute) {
            NavDestination.Home -> {
                closeCount++
                if (closeCount == 2) {
                    activity?.finish()
                    return@BackHandler
                }
                coroutineScope.launch {
                    onShowSnackBar()
                    closeCount = 0
                }
            }

            NavDestination.Camera -> {
                if (nfceState is Result.Loading) return@BackHandler

                onPopBack()
            }
        }
    }
}
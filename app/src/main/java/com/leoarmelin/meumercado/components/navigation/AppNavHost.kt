package com.leoarmelin.meumercado.components.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.screens.CameraScreen
import com.leoarmelin.meumercado.screens.HomeScreen
import com.leoarmelin.meumercado.viewmodels.CameraViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import kotlinx.coroutines.launch

@androidx.camera.core.ExperimentalGetImage
@Composable
fun AppNavHost(
    scaffoldState: ScaffoldState,
    cameraViewModel: CameraViewModel,
    navigationViewModel: NavigationViewModel
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as? Activity
    var closeCount by remember { mutableStateOf(0) }

    BackHandler {
        when (navigationViewModel.currentRoute) {
            NavDestination.Home.routeName -> {
                closeCount++
                if (closeCount == 2) {
                    activity?.finish()
                    return@BackHandler
                }
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Press again to close the app.")
                    closeCount = 0
                }
            }
            NavDestination.Camera.routeName -> {
                navigationViewModel.setRoute(NavDestination.Home.routeName)
            }
        }
    }

    NavHost(navController = navController, startDestination = NavDestination.Home.routeName) {
        composable(NavDestination.Home.routeName) {
            HomeScreen(navigationViewModel, cameraViewModel.isPermissionGranted)
        }

        composable(NavDestination.Camera.routeName) {
            CameraScreen(cameraViewModel)
        }
    }

    navController.navigate(navigationViewModel.currentRoute)
}
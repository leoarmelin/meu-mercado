package com.leoarmelin.meumercado.ui.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.leoarmelin.meumercado.models.api.ResultState
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.screens.CameraScreen
import com.leoarmelin.meumercado.ui.screens.SplashScreen
import com.leoarmelin.meumercado.ui.screens.StartScreen
import com.leoarmelin.meumercado.ui.screens.TicketScreen
import com.leoarmelin.meumercado.ui.screens.home_screen.HomeScreen
import com.leoarmelin.meumercado.ui.theme.Primary500
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@androidx.camera.core.ExperimentalGetImage
@Composable
fun AppNavHost(
    scaffoldState: ScaffoldState,
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    padding: PaddingValues
) {
    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as? Activity
    var closeCount by remember { mutableStateOf(0) }

    BackHandler {
        when (navigationViewModel.currentRoute) {
            NavDestination.Start.routeName, NavDestination.Home.routeName, NavDestination.Splash.routeName -> {
                closeCount++
                if (closeCount == 2) {
                    activity?.finish()
                    return@BackHandler
                }
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Pressione novamente para fechar.")
                    closeCount = 0
                }
            }
            NavDestination.Camera.routeName -> {
                if (mainViewModel.ticketResultState == ResultState.Loading) return@BackHandler
                navigationViewModel.popBack()
            }
            NavDestination.Ticket.routeName -> {
                navigationViewModel.setRoute(NavDestination.Home.routeName)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavDestination.Splash.routeName,
        modifier = Modifier.padding(padding)
    ) {
        composable(NavDestination.Splash.routeName) {
            SplashScreen(navigationViewModel, mainViewModel)
        }

        composable(NavDestination.Start.routeName) {
            StartScreen(navigationViewModel, mainViewModel.isPermissionGranted)
        }

        composable(NavDestination.Camera.routeName) {
            CameraScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel
            )
        }

        composable(NavDestination.Ticket.routeName) {
            TicketScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel,
                isCameraPermissionGranted = mainViewModel.isPermissionGranted
            )
        }

        composable(NavDestination.Home.routeName) {
            HomeScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel,
                isCameraPermissionGranted = mainViewModel.isPermissionGranted,
                ticketsList = mainViewModel.ticketsList
            )
        }
    }

    when (navigationViewModel.currentRoute) {
        NavDestination.Start.routeName, NavDestination.Splash.routeName -> {
            systemUiController.setStatusBarColor(Secondary50)
            systemUiController.setNavigationBarColor(Secondary50)
        }
        NavDestination.Camera.routeName -> {
            systemUiController.setStatusBarColor(Color.Black)
            systemUiController.setNavigationBarColor(Color.Black)
        }
        NavDestination.Ticket.routeName -> {
            systemUiController.setStatusBarColor(Secondary50)
            systemUiController.setNavigationBarColor(Primary500)
        }
        NavDestination.Home.routeName -> {
            systemUiController.setStatusBarColor(Secondary50)
            systemUiController.setNavigationBarColor(Primary500)
        }
    }

    navController.navigate(navigationViewModel.currentRoute)
}
package com.leoarmelin.meumercado.ui.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.leoarmelin.meumercado.ui.screens.CameraScreen
import com.leoarmelin.meumercado.ui.screens.HomeScreen
import com.leoarmelin.meumercado.ui.screens.SplashScreen
import com.leoarmelin.meumercado.ui.theme.Primary500
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.api.ResultState
import com.leoarmelin.sharedmodels.navigation.NavDestination
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
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
    val navController = rememberAnimatedNavController()
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as? Activity
    var closeCount by remember { mutableStateOf(0) }
    val navAnimationDuration = remember { 500 }

    val getNfceState by mainViewModel.getNfceState.collectAsState()
    val isPermissionGranted by mainViewModel.isCameraPermissionGranted.collectAsState()

    BackHandler {
        when (navigationViewModel.currentRoute) {
            NavDestination.Home -> {
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
            NavDestination.Camera -> {
                if (getNfceState == ResultState.Loading) return@BackHandler
                navigationViewModel.popBack()
            }
            NavDestination.Splash -> {}
        }
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = NavDestination.Splash.route,
        modifier = Modifier.padding(padding)
    ) {
        composable(
            NavDestination.Splash.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(navAnimationDuration)
                )
            }
        ) {
            SplashScreen(navigationViewModel, mainViewModel)
        }

        composable(
            NavDestination.Camera.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(navAnimationDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(navAnimationDuration)
                )
            }
        ) {
            CameraScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel
            )
        }

        composable(NavDestination.Home.route) {
            HomeScreen(mainViewModel, navigationViewModel)
        }
    }

    when (navigationViewModel.currentRoute) {
        NavDestination.Splash -> {
            systemUiController.setStatusBarColor(Secondary50)
            systemUiController.setNavigationBarColor(Secondary50)
        }
        NavDestination.Camera -> {
            systemUiController.setStatusBarColor(if (isPermissionGranted) Color.Black else Secondary50)
            systemUiController.setNavigationBarColor(if (isPermissionGranted) Color.Black else Secondary50)
        }
        NavDestination.Home -> {
            systemUiController.setStatusBarColor(Secondary50)
            systemUiController.setNavigationBarColor(Primary500)
        }
    }

    LaunchedEffect(navigationViewModel.currentRoute) {
        navController.navigate(navigationViewModel.currentRoute.route)
    }
}
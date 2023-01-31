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
            NavDestination.Start.route, NavDestination.Home.route, NavDestination.Splash.route -> {
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
            NavDestination.Camera.route -> {
                if (getNfceState == ResultState.Loading) return@BackHandler
                navigationViewModel.popBack()
            }
            NavDestination.Ticket.route -> {
                navigationViewModel.setRoute(NavDestination.Home.route)
            }
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
            NavDestination.Start.route,
            enterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.route, NavDestination.Ticket.route -> AnimatedContentScope.SlideDirection.Right
                        else -> AnimatedContentScope.SlideDirection.Left
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.route, NavDestination.Ticket.route -> AnimatedContentScope.SlideDirection.Right
                        else -> AnimatedContentScope.SlideDirection.Left
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(navAnimationDuration)
                )
            }
        ) {
            StartScreen(navigationViewModel, isPermissionGranted)
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

        composable(
            NavDestination.Ticket.route,
            enterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.route -> AnimatedContentScope.SlideDirection.Right
                        else -> AnimatedContentScope.SlideDirection.Left
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    when (targetState.destination.route) {
                        NavDestination.Camera.route -> AnimatedContentScope.SlideDirection.Left
                        else -> AnimatedContentScope.SlideDirection.Right
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.route -> AnimatedContentScope.SlideDirection.Right
                        else -> AnimatedContentScope.SlideDirection.Left
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    when (targetState.destination.route) {
                        NavDestination.Camera.route -> AnimatedContentScope.SlideDirection.Left
                        else -> AnimatedContentScope.SlideDirection.Right
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            }
        ) {
            TicketScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel,
            )
        }

        composable(
            NavDestination.Home.route,
            enterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.route, NavDestination.Ticket.route -> AnimatedContentScope.SlideDirection.Right
                        else -> AnimatedContentScope.SlideDirection.Left
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.route, NavDestination.Ticket.route -> AnimatedContentScope.SlideDirection.Right
                        else -> AnimatedContentScope.SlideDirection.Left
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(navAnimationDuration)
                )
            }
        ) {
            HomeScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel,
            )
        }
    }

    when (navigationViewModel.currentRoute) {
        NavDestination.Start.route, NavDestination.Splash.route -> {
            systemUiController.setStatusBarColor(Secondary50)
            systemUiController.setNavigationBarColor(Secondary50)
        }
        NavDestination.Camera.route -> {
            systemUiController.setStatusBarColor(if (isPermissionGranted) Color.Black else Secondary50)
            systemUiController.setNavigationBarColor(if (isPermissionGranted) Color.Black else Secondary50)
        }
        NavDestination.Ticket.route -> {
            systemUiController.setStatusBarColor(Secondary50)
            systemUiController.setNavigationBarColor(Primary500)
        }
        NavDestination.Home.route -> {
            systemUiController.setStatusBarColor(Secondary50)
            systemUiController.setNavigationBarColor(Primary500)
        }
    }

    LaunchedEffect(navigationViewModel.currentRoute) {
        navController.navigate(navigationViewModel.currentRoute)
    }
}
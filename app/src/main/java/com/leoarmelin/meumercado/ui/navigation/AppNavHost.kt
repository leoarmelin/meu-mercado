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
                if (getNfceState == ResultState.Loading) return@BackHandler
                navigationViewModel.popBack()
            }
            NavDestination.Ticket.routeName -> {
                navigationViewModel.setRoute(NavDestination.Home.routeName)
            }
        }
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = NavDestination.Splash.routeName,
        modifier = Modifier.padding(padding)
    ) {
        composable(
            NavDestination.Splash.routeName,
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
            NavDestination.Start.routeName,
            enterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.routeName, NavDestination.Ticket.routeName -> AnimatedContentScope.SlideDirection.Right
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
                        NavDestination.Camera.routeName, NavDestination.Ticket.routeName -> AnimatedContentScope.SlideDirection.Right
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
            NavDestination.Camera.routeName,
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
            NavDestination.Ticket.routeName,
            enterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.routeName -> AnimatedContentScope.SlideDirection.Right
                        else -> AnimatedContentScope.SlideDirection.Left
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    when (targetState.destination.route) {
                        NavDestination.Camera.routeName -> AnimatedContentScope.SlideDirection.Left
                        else -> AnimatedContentScope.SlideDirection.Right
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.routeName -> AnimatedContentScope.SlideDirection.Right
                        else -> AnimatedContentScope.SlideDirection.Left
                    },
                    animationSpec = tween(navAnimationDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    when (targetState.destination.route) {
                        NavDestination.Camera.routeName -> AnimatedContentScope.SlideDirection.Left
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
            NavDestination.Home.routeName,
            enterTransition = {
                slideIntoContainer(
                    when (initialState.destination.route) {
                        NavDestination.Camera.routeName, NavDestination.Ticket.routeName -> AnimatedContentScope.SlideDirection.Right
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
                        NavDestination.Camera.routeName, NavDestination.Ticket.routeName -> AnimatedContentScope.SlideDirection.Right
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
        NavDestination.Start.routeName, NavDestination.Splash.routeName -> {
            systemUiController.setStatusBarColor(Secondary50)
            systemUiController.setNavigationBarColor(Secondary50)
        }
        NavDestination.Camera.routeName -> {
            systemUiController.setStatusBarColor(if (isPermissionGranted) Color.Black else Secondary50)
            systemUiController.setNavigationBarColor(if (isPermissionGranted) Color.Black else Secondary50)
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

    LaunchedEffect(navigationViewModel.currentRoute) {
        navController.navigate(navigationViewModel.currentRoute)
    }
}
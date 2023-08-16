package com.leoarmelin.meumercado.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.leoarmelin.meumercado.ui.screens.CameraScreen
import com.leoarmelin.meumercado.ui.screens.CategoryScreen
import com.leoarmelin.meumercado.ui.screens.HomeScreen
import com.leoarmelin.meumercado.ui.screens.TicketScreen
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.navigation.NavDestination

@OptIn(ExperimentalAnimationApi::class)
@androidx.camera.core.ExperimentalGetImage
@Composable
fun AppNavHost(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    snackbarHostState: SnackbarHostState,
) {
    val navController = rememberAnimatedNavController()

    val nfceState by mainViewModel.ticketResult.collectAsState()
    val currentRoute by navigationViewModel.currentRoute.collectAsState()

    AppBackHandler(
        currentRoute = currentRoute,
        nfceState = nfceState,
        onShowSnackBar = { snackbarHostState.showSnackbar("Pressione novamente para fechar.") },
        onPopBack = navigationViewModel::popBack
    )

    NavHost(
        navController = navController,
        startDestination = NavDestination.Home.route,
        modifier = Modifier
            .fillMaxSize()
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

        composable(
            "${NavDestination.Category.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            CategoryScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel
            )
        }

        composable(NavDestination.Ticket.route) {
            TicketScreen(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel,
                ticket = (currentRoute as? NavDestination.Ticket)?.ticket
            )
        }
    }

    LaunchedEffect(currentRoute) {
        when (val route = currentRoute) {
            is NavDestination.Camera,
            is NavDestination.Home,
            is NavDestination.Ticket -> navController.navigate(route.route)

            is NavDestination.Category -> navController.navigate("${route.route}/${route.id}")
            is NavDestination.NewProduct, is NavDestination.NewCategory -> {}
        }
    }
}
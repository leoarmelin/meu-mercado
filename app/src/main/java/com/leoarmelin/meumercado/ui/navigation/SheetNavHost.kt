package com.leoarmelin.meumercado.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.leoarmelin.meumercado.ui.screens.CategorySheetScreen
import com.leoarmelin.meumercado.ui.screens.HomeSheetScreen
import com.leoarmelin.meumercado.viewmodels.CategoryViewModel
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.navigation.NavDestination

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SheetNavHost(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    categoryViewModel: CategoryViewModel
) {
    val currentRoute by navigationViewModel.currentRoute.collectAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val height = remember(screenHeight) { (screenHeight - 220).dp }
    
    val navController = rememberAnimatedNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestination.Home.route,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        composable(NavDestination.Home.route) {
            HomeSheetScreen(mainViewModel = mainViewModel, navigationViewModel = navigationViewModel)
        }

        composable(NavDestination.NewCategory.route) {
            HomeSheetScreen(mainViewModel = mainViewModel, navigationViewModel = navigationViewModel)
        }
        
        composable(NavDestination.Camera.route) {
            Text(text = "Tomanocu", color = Color.Blue)
        }

        composable(
            "${NavDestination.Category.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            CategorySheetScreen(
                categoryViewModel = categoryViewModel,
                navigationViewModel = navigationViewModel
            )
        }
    }

    LaunchedEffect(currentRoute) {
        when (val route = currentRoute) {
            is NavDestination.Category -> navController.navigate("${route.route}/${route.id}")
            else -> navController.navigate(route.route)
        }
    }
}
package com.leoarmelin.meumercado.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.leoarmelin.meumercado.ui.screens.HomeSheetScreen
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.sharedmodels.navigation.NavDestination

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SheetNavHost(
    currentRoute: NavDestination,
    mainViewModel: MainViewModel
) {
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
            HomeSheetScreen(mainViewModel = mainViewModel)
        }
        
        composable(NavDestination.Camera.route) {
            Text(text = "Tomanocu", color = Color.Blue)
        }
    }
    
    LaunchedEffect(currentRoute) {
        navController.navigate(currentRoute.route)
    }
}
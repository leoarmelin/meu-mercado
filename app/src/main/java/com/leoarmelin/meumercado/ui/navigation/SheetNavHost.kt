package com.leoarmelin.meumercado.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.leoarmelin.sharedmodels.navigation.NavDestination

@Composable
fun SheetNavHost(
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val height = remember(screenHeight) { (screenHeight - 220).dp }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.White)
    )
}
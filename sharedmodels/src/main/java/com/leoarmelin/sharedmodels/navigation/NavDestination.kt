package com.leoarmelin.sharedmodels.navigation

sealed class NavDestination(val route: String) {
    object Camera: NavDestination("camera")
    object Home: NavDestination("home")
}
package com.leoarmelin.sharedmodels.navigation

sealed class NavDestination(val route: String) {
    object Splash: NavDestination("splash")
    object Camera: NavDestination("camera")
    object Ticket: NavDestination("ticket")
    object Home: NavDestination("home")
}
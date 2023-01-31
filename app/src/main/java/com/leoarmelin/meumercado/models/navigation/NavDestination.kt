package com.leoarmelin.meumercado.models.navigation

sealed class NavDestination(val route: String) {
    object Splash: NavDestination("splash")
    object Camera: NavDestination("camera")
    object Start: NavDestination("start")
    object Ticket: NavDestination("ticket")
    object Home: NavDestination("home")
}
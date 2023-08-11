package com.leoarmelin.meumercado.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.leoarmelin.sharedmodels.navigation.NavDestination

class NavigationViewModel : ViewModel() {
    var currentRoute by mutableStateOf<NavDestination>(NavDestination.Home)
    var lastRoute by mutableStateOf<NavDestination?>(null)

    fun setRoute(route: NavDestination) {
        lastRoute = currentRoute
        currentRoute = route
    }

    fun popBack() {
        val currentLastRoute = lastRoute ?: return
        currentRoute = currentLastRoute
        lastRoute = null
    }
}
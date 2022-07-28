package com.leoarmelin.meumercado.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.leoarmelin.meumercado.models.navigation.NavDestination

class NavigationViewModel : ViewModel() {
    var currentRoute by mutableStateOf(NavDestination.Start.routeName)
    var lastRoute by mutableStateOf<String?>(null)

    fun setRoute(route: String) {
        lastRoute = currentRoute
        currentRoute = route
    }

    fun popBack() {
        val currentLastRoute = lastRoute ?: return
        currentRoute = currentLastRoute
        lastRoute = null
    }
}
package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.sharedmodels.navigation.NavDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {
    private val _currentRoute = MutableStateFlow<NavDestination>(NavDestination.Home)
    val currentRoute get() = _currentRoute.asStateFlow()

    private val _routeHistory = MutableStateFlow<List<NavDestination>>(listOf(NavDestination.Home))

    private val _fabDestinations = MutableStateFlow<List<NavDestination>>(emptyList())
    val fabDestinations get() = _fabDestinations.asStateFlow()

    init {
        viewModelScope.launch {
            _currentRoute.collect {
                _fabDestinations.value = when (it) {
                    is NavDestination.Home -> listOf(
                        NavDestination.NewCategory(),
                        NavDestination.Camera
                    )

                    is NavDestination.Category -> listOf(
                        NavDestination.NewProduct(),
                        NavDestination.Camera
                    )

                    else -> emptyList()
                }
            }
        }
    }

    fun setRoute(route: NavDestination) {
        val routeHistory = _routeHistory.value.toMutableList()

        when {
            route is NavDestination.NewProduct && routeHistory.lastOrNull() is NavDestination.NewProduct -> {
                routeHistory.removeLast()
            }
        }

        routeHistory.add(route)
        _routeHistory.value = routeHistory

        _currentRoute.value = route
    }

    fun popBack() {
        val routeHistory = _routeHistory.value.toMutableList()
        _routeHistory.value = routeHistory.dropLast(1)
        _routeHistory.value.lastOrNull()?.let {
            _currentRoute.value = it
        }
    }

    fun popAllBack() {
        _routeHistory.value = listOf(NavDestination.Home)
        _currentRoute.value = _routeHistory.value.first()
    }
}
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

    private val _lastRoute = MutableStateFlow<NavDestination?>(null)

    private val _fabDestinations = MutableStateFlow<List<NavDestination>>(emptyList())
    val fabDestinations get() = _fabDestinations.asStateFlow()

    init {
        viewModelScope.launch {
            _currentRoute.collect {
                _fabDestinations.value = when (it) {
                    is NavDestination.Home -> listOf(NavDestination.NewCategory, NavDestination.Camera)
                    is NavDestination.Category -> listOf(NavDestination.NewProduct, NavDestination.Camera)
                    else -> emptyList()
                }
            }
        }
    }

    fun setRoute(route: NavDestination) {
        _lastRoute.value = _currentRoute.value
        _currentRoute.value = route
    }

    fun popBack() {
        val currentLastRoute = _lastRoute.value ?: return
        _currentRoute.value = currentLastRoute
        _lastRoute.value = null
    }
}
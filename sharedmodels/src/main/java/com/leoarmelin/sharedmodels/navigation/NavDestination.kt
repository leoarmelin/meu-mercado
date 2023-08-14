package com.leoarmelin.sharedmodels.navigation

sealed class NavDestination(val route: String) {
    object Camera: NavDestination("camera")
    object Home: NavDestination("home")
    class Category(val id: String): NavDestination("category") {
        companion object {
            const val route = "category"
        }
    }
    object NewProduct: NavDestination("new-product")
    object NewCategory: NavDestination("new-category")
}
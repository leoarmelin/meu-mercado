package com.leoarmelin.sharedmodels.navigation

import com.leoarmelin.sharedmodels.Product

sealed class NavDestination(val route: String) {
    object Camera : NavDestination("camera")
    object Home : NavDestination("home")
    class Category(val id: String) : NavDestination("category") {
        companion object {
            const val route = "category"
        }
    }
    class NewProduct(val product: Product? = null) : NavDestination("new-product")
    class NewCategory(val category: com.leoarmelin.sharedmodels.Category? = null) :
        NavDestination("new-category")
    class Ticket(val ticket: com.leoarmelin.sharedmodels.Ticket) : NavDestination("ticket"){
        companion object {
            const val route = "ticket"
        }
    }
}
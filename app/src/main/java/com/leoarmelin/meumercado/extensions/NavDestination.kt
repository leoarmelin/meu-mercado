package com.leoarmelin.meumercado.extensions

import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.sharedmodels.navigation.NavDestination

val NavDestination.fabOption
    get() = when (this) {
        NavDestination.Camera -> Strings.FAB.camera
        NavDestination.NewProduct -> Strings.FAB.product
        NavDestination.NewCategory -> Strings.FAB.category
        else -> ""
    }
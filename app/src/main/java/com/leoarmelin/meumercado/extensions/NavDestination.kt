package com.leoarmelin.meumercado.extensions

import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.sharedmodels.navigation.NavDestination

val NavDestination.fabOption
    get() = when (this) {
        is NavDestination.Camera -> Strings.FAB.camera
        is NavDestination.NewProduct -> Strings.FAB.product
        is NavDestination.NewCategory -> Strings.FAB.category
        else -> ""
    }
package com.leoarmelin.meumercado.ui.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.sharedmodels.api.Result
import com.leoarmelin.sharedmodels.navigation.NavDestination
import kotlinx.coroutines.launch

@Composable
fun AppBackHandler(
    currentRoute: NavDestination,
    nfceState: Result<Ticket>?,
    onShowSnackBar: suspend () -> Unit,
    onPopBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as? Activity
    var closeCount by remember { mutableStateOf(0) }

    BackHandler {
        when (currentRoute) {
            is NavDestination.Home -> {
                closeCount++
                if (closeCount == 2) {
                    activity?.finish()
                    return@BackHandler
                }
                coroutineScope.launch {
                    onShowSnackBar()
                    closeCount = 0
                }
            }

            is NavDestination.Camera -> {
                if (nfceState is Result.Loading) return@BackHandler

                onPopBack()
            }

            is NavDestination.NewCategory,
            is NavDestination.NewProduct,
            is NavDestination.Category,
            is NavDestination.Ticket -> {
                onPopBack()
            }
        }
    }
}
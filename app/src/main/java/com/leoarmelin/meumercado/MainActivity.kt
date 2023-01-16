package com.leoarmelin.meumercado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.leoarmelin.meumercado.handlers.PermissionsHandler
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.components.CameraPermissionDialog
import com.leoarmelin.meumercado.ui.navigation.AppNavHost
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@ExperimentalAnimationApi
@androidx.camera.core.ExperimentalGetImage
@AndroidEntryPoint
class MainActivity : ComponentActivity(), PermissionsHandler.AccessListener {
    val permissionsHandler = PermissionsHandler(this, this)

    private val mainViewModel: MainViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeuMercadoTheme {
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    AppNavHost(
                        mainViewModel = mainViewModel,
                        navigationViewModel = navigationViewModel,
                        scaffoldState = scaffoldState,
                        padding = padding,
                    )
                }

                CameraPermissionDialog(mainViewModel.isPermissionDialogOpen) {
                    mainViewModel.isPermissionDialogOpen = false
                    permissionsHandler.launchPermissionRequest()
                }
            }
        }
    }

    override fun onGrantedCameraAccess() {
        mainViewModel.setCameraPermissionState(true)
        navigationViewModel.setRoute(NavDestination.Camera.routeName)
    }

    override fun onNotGrantedCameraAccess() {
        mainViewModel.setCameraPermissionState(false)
        navigationViewModel.setRoute(NavDestination.Camera.routeName)
    }

    override fun onShowCameraUIAccess() {
        mainViewModel.isPermissionDialogOpen = true
    }
}
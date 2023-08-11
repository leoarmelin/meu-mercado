package com.leoarmelin.meumercado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.leoarmelin.meumercado.handlers.PermissionsHandler
import com.leoarmelin.meumercado.ui.components.CameraPermissionDialog
import com.leoarmelin.meumercado.ui.navigation.AppMainSheetScreen
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.navigation.NavDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@androidx.camera.core.ExperimentalGetImage
@AndroidEntryPoint
class MainActivity : ComponentActivity(), PermissionsHandler.AccessListener {
    val permissionsHandler = PermissionsHandler(this, this)

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val mainViewModel: MainViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = Firebase.analytics

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                mainViewModel.fetchAllCategories()
            }
        }

        setContent {
            MeuMercadoTheme {
                val systemUiController = rememberSystemUiController()
                val isPermissionDialogOpen by mainViewModel.isCameraPermissionDialogOpen.collectAsState()

                LaunchedEffect(Unit) {
                    systemUiController.setStatusBarColor(Color(0xFFDEDEDE))
                    systemUiController.setNavigationBarColor(Color.White)
                }

                AppMainSheetScreen(
                    mainViewModel = mainViewModel,
                    navigationViewModel = navigationViewModel,
                )

                CameraPermissionDialog(isPermissionDialogOpen) {
                    mainViewModel.togglePermissionDialog(false)
                    permissionsHandler.launchPermissionRequest()
                }
            }
        }
    }

    override fun onGrantedCameraAccess() {
        mainViewModel.setCameraPermissionState(true)
        navigationViewModel.setRoute(NavDestination.Camera)
    }

    override fun onNotGrantedCameraAccess() {
        mainViewModel.setCameraPermissionState(false)
        navigationViewModel.setRoute(NavDestination.Camera)
    }

    override fun onShowCameraUIAccess() {
        mainViewModel.togglePermissionDialog(true)
    }
}
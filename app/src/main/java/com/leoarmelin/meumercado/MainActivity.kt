package com.leoarmelin.meumercado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.leoarmelin.meumercado.handlers.PermissionsHandler
import com.leoarmelin.meumercado.ui.components.AppFAB
import com.leoarmelin.meumercado.ui.components.CameraPermissionDialog
import com.leoarmelin.meumercado.ui.components.DatePicker
import com.leoarmelin.meumercado.ui.screens.MainScreen
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.viewmodels.CategoryViewModel
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.navigation.NavDestination
import dagger.hilt.android.AndroidEntryPoint

@androidx.camera.core.ExperimentalGetImage
@AndroidEntryPoint
class MainActivity : ComponentActivity(), PermissionsHandler.AccessListener {
    val permissionsHandler = PermissionsHandler(this, this)

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val mainViewModel: MainViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = Firebase.analytics

        setContent {
            MeuMercadoTheme {
                val systemUiController = rememberSystemUiController()
                val isPermissionDialogOpen by mainViewModel.isCameraPermissionDialogOpen.collectAsState()
                val isDatePickerOpen by mainViewModel.isDatePickerOpen.collectAsState()
                val selectedDate by mainViewModel.selectedDate.collectAsState()
                val fabDestinations by navigationViewModel.fabDestinations.collectAsState()

                LaunchedEffect(Unit) {
                    systemUiController.setStatusBarColor(Color(0xFFDEDEDE))
                    systemUiController.setNavigationBarColor(Color.White)
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    MainScreen(
                        mainViewModel = mainViewModel,
                        navigationViewModel = navigationViewModel,
                        categoryViewModel = categoryViewModel
                    )

                    DatePicker(
                        modifier = Modifier
                            .padding(top = 24.dp, start = 12.dp)
                            .align(Alignment.TopStart),
                        isOpen = isDatePickerOpen,
                        currentDate = selectedDate,
                        onApply = mainViewModel::selectDate,
                        onClose = {
                            mainViewModel.toggleDatePicker(false)
                        }
                    )

                    AppFAB(
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 32.dp)
                            .align(Alignment.BottomEnd),
                        destinations = fabDestinations,
                        onSelect = navigationViewModel::setRoute
                    )

                    CameraPermissionDialog(isPermissionDialogOpen) {
                        mainViewModel.togglePermissionDialog(false)
                        permissionsHandler.launchPermissionRequest()
                    }
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
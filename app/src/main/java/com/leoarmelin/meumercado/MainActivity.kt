package com.leoarmelin.meumercado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.leoarmelin.meumercado.handlers.PermissionsHandler
import com.leoarmelin.meumercado.ui.components.AppFAB
import com.leoarmelin.meumercado.ui.components.CameraPermissionDialog
import com.leoarmelin.meumercado.ui.components.DatePicker
import com.leoarmelin.meumercado.ui.components.EmojiPicker
import com.leoarmelin.meumercado.ui.navigation.AppNavHost
import com.leoarmelin.meumercado.ui.theme.CreamOne
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.ui.theme.White
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.navigation.NavDestination
import dagger.hilt.android.AndroidEntryPoint

@androidx.camera.core.ExperimentalGetImage
@AndroidEntryPoint
class MainActivity : ComponentActivity(), PermissionsHandler.AccessListener {
    private val permissionsHandler = PermissionsHandler(this, this)

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val mainViewModel: MainViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = Firebase.analytics

        setContent {
            MeuMercadoTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val systemUiController = rememberSystemUiController()
                val isPermissionDialogOpen by mainViewModel.isCameraPermissionDialogOpen.collectAsStateWithLifecycle()
                val isDatePickerOpen by mainViewModel.isDatePickerOpen.collectAsStateWithLifecycle()
                val selectedDate by mainViewModel.selectedDate.collectAsStateWithLifecycle()
                val fabDestinations by navigationViewModel.fabDestinations.collectAsStateWithLifecycle()
                val isCameraPermissionGranted by mainViewModel.isCameraPermissionGranted.collectAsStateWithLifecycle()
                val isEmojiPickerOpen by mainViewModel.isEmojiPickerOpen.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) {
                    systemUiController.setStatusBarColor(White)
                    systemUiController.setNavigationBarColor(CreamOne)
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        AppNavHost(
                            mainViewModel = mainViewModel,
                            navigationViewModel = navigationViewModel,
                            snackbarHostState = snackbarHostState
                        )

                        DatePicker(
                            modifier = Modifier
                                .padding(top = 24.dp, start = 12.dp)
                                .align(Alignment.TopStart),
                            isOpen = isDatePickerOpen,
                            dateInterval = selectedDate,
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
                            onSelect = { destination ->
                                when (destination) {
                                    is NavDestination.Camera -> {
                                        if (isCameraPermissionGranted) {
                                            navigationViewModel.setRoute(NavDestination.Camera)
                                        } else {
                                            permissionsHandler.requestCameraPermission()
                                        }
                                    }

                                    else -> {
                                        navigationViewModel.setRoute(destination)
                                    }
                                }
                            }
                        )

                        if (isEmojiPickerOpen) {
                            EmojiPicker(onEmojiTap = mainViewModel::setEmoji)
                        }

                        CameraPermissionDialog(isPermissionDialogOpen) {
                            mainViewModel.togglePermissionDialog(false)
                            permissionsHandler.launchPermissionRequest()
                        }
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
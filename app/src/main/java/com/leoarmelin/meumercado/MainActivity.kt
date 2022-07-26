package com.leoarmelin.meumercado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.leoarmelin.meumercado.ui.navigation.AppNavHost
import com.leoarmelin.meumercado.handlers.PermissionsHandler
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.viewmodels.CameraViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel

@androidx.camera.core.ExperimentalGetImage
class MainActivity : ComponentActivity(), PermissionsHandler.AccessListener {
    val permissionsHandler = PermissionsHandler(this, this)

    private val cameraViewModel: CameraViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeuMercadoTheme {
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavHost(
                        cameraViewModel = cameraViewModel,
                        navigationViewModel = navigationViewModel,
                        scaffoldState = scaffoldState
                    )
                }
            }
        }
    }

    override fun onGrantedCameraAccess() {
        cameraViewModel.setCameraPermissionState(true)
        navigationViewModel.setRoute(NavDestination.Camera.routeName)
    }

    override fun onNotGrantedCameraAccess() {
        cameraViewModel.setCameraPermissionState(false)
    }

    override fun onShowCameraUIAccess() {
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeuMercadoTheme {
        Greeting("Android")
    }
}
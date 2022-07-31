package com.leoarmelin.meumercado

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.leoarmelin.meumercado.ui.navigation.AppNavHost
import com.leoarmelin.meumercado.handlers.PermissionsHandler
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel

@ExperimentalPagerApi
@androidx.camera.core.ExperimentalGetImage
class MainActivity : ComponentActivity(), PermissionsHandler.AccessListener {
    val permissionsHandler = PermissionsHandler(this, this)

    private lateinit var mainViewModel: MainViewModel
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = MainViewModel(application)

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
            }
        }
    }

    override fun onGrantedCameraAccess() {
        mainViewModel.setCameraPermissionState(true)
        navigationViewModel.setRoute(NavDestination.Camera.routeName)
    }

    override fun onNotGrantedCameraAccess() {
        mainViewModel.setCameraPermissionState(false)
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
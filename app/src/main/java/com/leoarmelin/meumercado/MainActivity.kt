package com.leoarmelin.meumercado

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.leoarmelin.meumercado.components.CameraView
import com.leoarmelin.meumercado.handlers.PermissionsHandler
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme

@androidx.camera.core.ExperimentalGetImage
class MainActivity : ComponentActivity(), PermissionsHandler.AccessListener {
    private val permissionsHandler = PermissionsHandler(this, this)
    var isPermissionGranted by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val barcodeScannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            ).build()
        val barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions)

        setContent {
            MeuMercadoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (isPermissionGranted) {
                        CameraView(
                            modifier = Modifier.fillMaxSize(),
                            barcodeScanner = barcodeScanner,
                            barcodeSuccessCallback = { url -> Log.d("Aoba", "url:$url") },
                            barcodeTypeCallback = { Log.d("Aoba", "Unexpected code type")},
                            barcodeFailureCallback = { errorMessage -> Log.d("Aoba", "Failed: $errorMessage") }
                        )
                    } else {
                        permissionsHandler.requestCameraPermission()
                    }
                }
            }
        }
    }

    override fun onGrantedCameraAccess() {
        isPermissionGranted = true
    }

    override fun onNotGrantedCameraAccess() {
        isPermissionGranted = false
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
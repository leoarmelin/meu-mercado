package com.leoarmelin.meumercado.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.leoarmelin.meumercado.components.CameraView
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.viewmodels.CameraViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel

@androidx.camera.core.ExperimentalGetImage
@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel,
    navigationViewModel: NavigationViewModel
) {
    val barcodeScannerOptions = remember {
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            ).build()
    }
    val barcodeScanner = remember {
        BarcodeScanning.getClient(barcodeScannerOptions)
    }

    var isSearching by remember { mutableStateOf(false) }

    SideEffect {
        if (cameraViewModel.ticket != null) {
            navigationViewModel.setRoute(NavDestination.Home.routeName)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraViewModel.isPermissionGranted) {
            CameraView(
                modifier = Modifier.fillMaxSize(),
                barcodeScanner = barcodeScanner,
                barcodeSuccessCallback = { url -> if (!isSearching) {
                    isSearching = true
                    cameraViewModel.getNfce(url)
                } },
                barcodeTypeCallback = { Log.d("Aoba", "Unexpected code type") },
                barcodeFailureCallback = { errorMessage -> Log.d("Aoba", "Failed: $errorMessage") }
            )
        }
    }
}
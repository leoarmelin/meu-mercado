package com.leoarmelin.meumercado.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.leoarmelin.meumercado.components.CameraView
import com.leoarmelin.meumercado.viewmodels.CameraViewModel

@androidx.camera.core.ExperimentalGetImage
@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel
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

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraViewModel.isPermissionGranted) {
            CameraView(
                modifier = Modifier.fillMaxSize(),
                barcodeScanner = barcodeScanner,
                barcodeSuccessCallback = { url -> Log.d("Aoba", "url:$url") },
                barcodeTypeCallback = { Log.d("Aoba", "Unexpected code type") },
                barcodeFailureCallback = { errorMessage -> Log.d("Aoba", "Failed: $errorMessage") }
            )
        }
    }
}
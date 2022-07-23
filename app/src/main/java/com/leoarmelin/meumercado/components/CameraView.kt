package com.leoarmelin.meumercado.components

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.leoarmelin.meumercado.extensions.getCameraProvider

@androidx.camera.core.ExperimentalGetImage
@Composable
fun CameraView(
    modifier: Modifier = Modifier,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    barcodeScanner: BarcodeScanner,
    barcodeTypeCallback: () -> Unit,
    barcodeSuccessCallback: (url: String) -> Unit,
    barcodeFailureCallback: (errorMessage: String?) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val previewView = remember { PreviewView(context) }

    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()

    imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
        val mediaImage = imageProxy.image ?: return@setAnalyzer

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(image).addOnSuccessListener { barcodes ->
            for (barcode in barcodes) {
                when (barcode.valueType) {
                    Barcode.TYPE_URL -> {
                        val url = barcode.url?.url

                        if (url.isNullOrEmpty()) {
                            barcodeFailureCallback("Url not found")
                            return@addOnSuccessListener
                        }

                        barcodeSuccessCallback(url)
                    }

                    else -> {
                        barcodeTypeCallback()
                    }
                }
            }
        }.addOnFailureListener {
            barcodeFailureCallback(it.message)
        }.addOnCompleteListener {
            imageProxy.close()
        }
    }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            imageAnalysis,
            preview,
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {

        }
    }
}
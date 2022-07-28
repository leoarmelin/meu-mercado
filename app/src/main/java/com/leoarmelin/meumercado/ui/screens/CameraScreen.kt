package com.leoarmelin.meumercado.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.leoarmelin.meumercado.R
import com.leoarmelin.meumercado.ui.components.CameraView
import com.leoarmelin.meumercado.extensions.gradientBackground
import com.leoarmelin.meumercado.extensions.noRippleClickable
import com.leoarmelin.meumercado.models.api.ResultState
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.components.LoadingDialog
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

    LaunchedEffect(cameraViewModel.ticketResultState) {
        when (val result = cameraViewModel.ticketResultState) {
            is ResultState.Loading -> {
                Log.d("Aoba", "Loading")
            }

            is ResultState.Success -> {
                Log.d("Aoba", "Success")
                navigationViewModel.setRoute(NavDestination.Ticket.routeName)
            }

            is ResultState.Error -> {
                Log.d("Aoba", "Error ${result.exception}")
            }

            else -> {
                Log.d("Aoba", "else :)")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraViewModel.isPermissionGranted) {
            CameraView(
                modifier = Modifier.fillMaxSize(),
                barcodeScanner = barcodeScanner,
                barcodeSuccessCallback = { url ->
                    if (!isSearching) {
                        isSearching = true
                        cameraViewModel.getNfce(url)
                    }
                },
                barcodeTypeCallback = { Log.d("Aoba", "Unexpected code type") },
                barcodeFailureCallback = { errorMessage -> Log.d("Aoba", "Failed: $errorMessage") }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .gradientBackground(listOf(Color(0x99000000), Color(0x00000000)), 270f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_camera_frame),
                contentDescription = "Aponte este quadrado deixando o QR Code dentro dele para adicionar os produtos.",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center), tint = Color(0x99BABABA)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "Botão para fechar a câmera.",
                modifier = Modifier
                    .padding(start = 28.dp, top = 30.dp)
                    .align(Alignment.TopStart)
                    .size(30.dp)
                    .noRippleClickable {
                        if (cameraViewModel.ticketResultState == ResultState.Loading) return@noRippleClickable
                        navigationViewModel.setRoute(NavDestination.Start.routeName)
                    }
                    .padding(6.dp),
                tint = Color.White
            )

            Text(
                text = "Mire sua câmera para o QR Code",
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 49.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color(0x4D000000), RoundedCornerShape(2.dp))
                    .padding(horizontal = 9.dp, vertical = 5.dp),
            )
        }

        LoadingDialog(cameraViewModel.ticketResultState == ResultState.Loading)
    }
}
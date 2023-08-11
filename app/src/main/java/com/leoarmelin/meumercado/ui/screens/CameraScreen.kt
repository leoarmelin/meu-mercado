package com.leoarmelin.meumercado.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.leoarmelin.cameraview.CameraView
import com.leoarmelin.meumercado.R
import com.leoarmelin.meumercado.extensions.getActivity
import com.leoarmelin.meumercado.extensions.gradientBackground
import com.leoarmelin.meumercado.extensions.noRippleClickable
import com.leoarmelin.meumercado.ui.components.LoadingDialog
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.meumercado.ui.theme.White
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.api.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalGetImage
@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun CameraScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel
) {
    val searchCoroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current.getActivity()

    val ticketResult by mainViewModel.nfceState.collectAsState()
    val isPermissionGranted by mainViewModel.isCameraPermissionGranted.collectAsState()
    val openIntentResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
    )

    var isSearching by remember { mutableStateOf(false) }
    var isErrorVisible by remember { mutableStateOf(false) }

    LaunchedEffect(ticketResult) {
        when (val result = ticketResult) {
            is Result.Loading -> {
                Log.d("Aoba", "Loading")
                isErrorVisible = false
            }

            is Result.Success -> {
                Log.d("Aoba", "Success")
                //navigationViewModel.setRoute(NavDestination.Ticket)
                mainViewModel.clearTicketResult()
                isErrorVisible = false
            }

            is Result.Error -> {
                Log.d("Aoba", "Error ${result.exception}")
                isErrorVisible = true
                mainViewModel.clearTicketResult()
                searchCoroutineScope.launch {
                    delay(1000)
                    isSearching = false
                    delay(3000)
                    isErrorVisible = false
                }
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        if (isPermissionGranted) {
            CameraView(
                modifier = Modifier.fillMaxSize(),
                barcodeSuccessCallback = { url ->
                    if (!isSearching) {
                        isSearching = true
                        mainViewModel.getNfce(url)
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
                painter = painterResource(R.drawable.ic_camera_frame),
                contentDescription = stringResource(R.string.aponte_este_quadrado_deixando_o),
                modifier = Modifier
                    .size(270.dp)
                    .align(Alignment.Center), tint = Color(0x99BABABA)
            )

            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = stringResource(R.string.botao_para_fechar_a_camera),
                modifier = Modifier
                    .padding(start = 28.dp, top = 30.dp)
                    .align(Alignment.TopStart)
                    .size(30.dp)
                    .noRippleClickable {
                        if (ticketResult is Result.Loading) return@noRippleClickable
                        navigationViewModel.popBack()
                    }
                    .padding(6.dp),
                tint = Color.White
            )

            Text(
                text = stringResource(R.string.mire_sua_camera_para_o_qr_code),
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 49.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color(0x4D000000), RoundedCornerShape(2.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp),
            )

            AnimatedVisibility(
                visible = isErrorVisible,
                enter = fadeIn(),
                exit = fadeOut(animationSpec = tween(3000)),
                modifier = Modifier
                    .padding(top = 60.dp)
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    text = stringResource(R.string.erro_ao_ler_o_qr_code),
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    modifier = Modifier
                        .background(White, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.voce_precisa_autorizar_a_camera),
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Center,
                    color = Black
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_sad_cam),
                    contentDescription = stringResource(R.string.icone_de_uma_camera_com_um_rosto_triste),
                    tint = Black,
                    modifier = Modifier
                        .width(118.dp)
                        .height(106.dp),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.va_para_configuracoes_e_garante),
                        style = MaterialTheme.typography.h5,
                        color = Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Button(
                        onClick = {
                            searchCoroutineScope.launch {
                                val appConfigIntent =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri: Uri = Uri.fromParts("package", activity?.packageName, null)
                                appConfigIntent.data = uri
                                openIntentResult.launch(appConfigIntent)
                            }
                        },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = White,
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.ir_para_configuracoes),
                            style = MaterialTheme.typography.h5,
                            color = Color.White
                        )
                    }
                }

            }
        }

        LoadingDialog(ticketResult is Result.Loading)
    }
}
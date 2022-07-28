package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.meumercado.ui.components.BottomCamera
import com.leoarmelin.meumercado.ui.components.ProductList
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.meumercado.viewmodels.CameraViewModel

@Composable
fun TicketScreen(cameraViewModel: CameraViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Secondary50)
    ) {
        cameraViewModel.ticket?.let { ProductList(it) }

        BottomCamera(
            totalPrice = (cameraViewModel.ticket?.price_total ?: 0.0).toMoney(),
            modifier = Modifier.align(Alignment.BottomStart)
        ) {}
    }
}
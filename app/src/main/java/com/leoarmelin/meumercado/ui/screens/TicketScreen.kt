package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.ui.components.ProductList
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.meumercado.ui.theme.Primary800
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(Primary800)
                .align(Alignment.BottomStart)
        ) {
            Text(
                style = MaterialTheme.typography.h4,
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(
                            "Valor total: "
                        )
                    }
                    append((cameraViewModel.ticket?.price_total ?: 0.0).toMoney())
                },
                color = Color.White,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}
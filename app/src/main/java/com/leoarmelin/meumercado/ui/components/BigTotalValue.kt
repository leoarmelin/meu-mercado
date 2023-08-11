package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.ui.theme.Black

@Composable
fun BigTotalValue(value: Double) {
    val intValue = remember(value) { value.toInt() }
    val decimalValue = remember(value) { getDecimalString(value) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "R$",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Black,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "$intValue",
            fontSize = 50.9.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )

        Text(
            text = decimalValue,
            fontSize = 25.5.sp,
            fontWeight = FontWeight.Bold,
            color = Black,
            modifier = Modifier.padding(top = 26.dp)
        )
    }
}

private fun getDecimalString(value: Double): String {
    val decimalValue = ((value - value.toInt()) * 100).toInt()

    return if (decimalValue < 10) ",0${decimalValue}" else ",${decimalValue}"
}
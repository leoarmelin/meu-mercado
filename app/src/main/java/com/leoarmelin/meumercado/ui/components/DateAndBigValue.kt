package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDateTime

@Composable
fun DateAndBigValue(
    date: LocalDateTime,
    totalValue: Double,
    onDateTap: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateSelector(
            date = date,
            onTap = onDateTap
        )
    }

    BigTotalValue(value = totalValue)
}
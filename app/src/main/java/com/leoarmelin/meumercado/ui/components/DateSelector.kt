package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DateSelector(date: LocalDateTime, onTap: () -> Unit) {
    val monthString = remember(date) { getMonthString(date) }
    val yearString = remember(date) { getYearString(date) }
    
    Row(
        modifier = Modifier.clickable { onTap() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = monthString, fontSize = 20.sp)

        Text(text = yearString, fontSize = 14.sp)
    }
}

private fun getMonthString(date: LocalDateTime) = date.format(
    DateTimeFormatter.ofPattern("MMM", Locale("pt", "BR"))
).replaceFirstChar { it.uppercase() }.dropLast(1)

private fun getYearString(date: LocalDateTime) = date.format(
    DateTimeFormatter.ofPattern("yyyy")
)

@Preview(
    showBackground = true
)
@Composable
private fun PreviewOne() {
    MeuMercadoTheme {
        DateSelector(
            date = LocalDateTime.now(),
            onTap = {}
        )
    }
}
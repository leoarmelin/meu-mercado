package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.meumercado.ui.theme.Strings

@Composable
fun ScreenHeader(modifier: Modifier = Modifier, title: String, onBack: () -> Unit) {
    Box(modifier = modifier.fillMaxWidth()) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(24.dp)
                .clickable { onBack() },
            imageVector = Icons.Rounded.ChevronLeft,
            contentDescription = Strings.PageHeader.back,
            tint = Black
        )

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            color = Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewOne() {
    ScreenHeader(
        title = "Adicionar categoria",
        onBack = {}
    )
}
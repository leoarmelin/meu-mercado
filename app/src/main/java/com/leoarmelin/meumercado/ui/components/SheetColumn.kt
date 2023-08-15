package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.ui.theme.Black

@Composable
fun SheetColumn(title: String, content: @Composable ColumnScope.() -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val height = remember(screenHeight) { (screenHeight - 220).dp }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Text(
            text = title,
            color = Black,
            fontSize = 14.sp,
        )

        content(this)
    }
}
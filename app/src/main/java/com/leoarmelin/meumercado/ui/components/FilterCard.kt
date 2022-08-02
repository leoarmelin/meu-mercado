package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.extensions.noRippleClickable
import com.leoarmelin.meumercado.ui.theme.Primary800
import com.leoarmelin.meumercado.ui.theme.Secondary800

@Composable
fun FilterCard(isClicked: Boolean, onClick: () -> Unit, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .height(20.dp)
            .background(if (isClicked) Secondary800 else Primary800, RoundedCornerShape(10.dp))
            .noRippleClickable { onClick() }
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}
package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.ui.theme.GrayOne

@Composable
fun EmojiField(
    value: String,
    onTap: () -> Unit
) {
    if (value.isNotEmpty()) {
        Text(
            text = value,
            fontSize = 24.sp,
            lineHeight = 24.sp,
            maxLines = 1,
            modifier = Modifier
                .size(32.dp)
                .clickable { onTap() }
        )
    } else {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .clickable { onTap() },
            imageVector = Icons.Filled.TagFaces,
            contentDescription = "",
            tint = GrayOne
        )
    }
}
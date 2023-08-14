package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.ui.theme.GrayOne

@Composable
fun EmojiTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .size(32.dp),
        singleLine = true,
        textStyle = TextStyle.Default.copy(fontSize = 24.sp, lineHeight = 24.sp),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Filled.TagFaces,
                    contentDescription = "",
                    tint = GrayOne
                )
            } else {
                innerTextField()
            }
        }
    )
}
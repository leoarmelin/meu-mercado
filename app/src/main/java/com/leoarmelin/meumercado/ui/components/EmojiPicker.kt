package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.emoji2.emojipicker.EmojiPickerView
import com.leoarmelin.meumercado.ui.theme.White

@Composable
fun EmojiPicker(onEmojiTap: (String) -> Unit) {
    AndroidView(
        factory = { context ->
            val view = EmojiPickerView(context)
            view.setOnEmojiPickedListener { onEmojiTap(it.emoji) }
            return@AndroidView view
        },
        modifier = Modifier.fillMaxSize().background(White),
    )
}
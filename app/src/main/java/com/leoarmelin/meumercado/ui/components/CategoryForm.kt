package com.leoarmelin.meumercado.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.ui.theme.Blue
import com.leoarmelin.meumercado.ui.theme.GrayOne
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.ui.theme.Strings

@Composable
fun CategoryForm(
    emoji: String,
    name: String,
    isButtonEnabled: Boolean,
    onEmojiTap: () -> Unit,
    onNameChange: (String) -> Unit,
    onSave: () -> Unit
) {
    val checkColor by animateColorAsState(
        targetValue = if (isButtonEnabled) Blue else GrayOne,
        label = "checkColor"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EmojiField(value = emoji, onTap = onEmojiTap)

        AppInput(
            modifier = Modifier.weight(1f),
            value = name,
            onValueChange = onNameChange,
            placeholder = Strings.AddCategory.namePlaceholder,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences
            )
        )

        Icon(
            modifier = Modifier
                .size(32.dp)
                .clickable(enabled = isButtonEnabled, onClick = onSave),
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = Strings.AddCategory.save,
            tint = checkColor
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewOne() {
    val emoji = "\uD83C\uDF63"
    var name by remember { mutableStateOf("") }

    MeuMercadoTheme {
        CategoryForm(
            emoji = emoji,
            name = name,
            isButtonEnabled = name.isNotEmpty(),
            onEmojiTap = {},
            onNameChange = { name = it },
            onSave = {},
        )
    }
}
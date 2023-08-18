package com.leoarmelin.meumercado.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.ui.theme.Blue
import com.leoarmelin.meumercado.ui.theme.GrayOne
import com.leoarmelin.meumercado.ui.theme.Red
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.meumercado.ui.theme.White
import com.leoarmelin.sharedmodels.Unity

@Composable
fun ProductForm(
    id: String?,
    emoji: String,
    name: String,
    unity: Unity,
    amount: String,
    unityPrice: String,
    isButtonEnabled: Boolean,
    onEmojiChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onUnityChange: (Unity) -> Unit,
    onAmountChange: (String) -> Unit,
    onUnityPriceChange: (String) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    val checkColor by animateColorAsState(
        targetValue = if (isButtonEnabled) Blue else GrayOne,
        label = "checkColor"
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EmojiTextField(value = emoji, onValueChange = onEmojiChange)

            AppInput(
                modifier = Modifier.weight(1f),
                value = name,
                onValueChange = onNameChange,
                placeholder = Strings.AddProduct.namePlaceholder,
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

            if (id != null) {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable { onDelete() }
                        .background(Red)
                        .padding(3.dp),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = Strings.AddCategory.delete,
                    tint = White
                )
            }
        }

        Row(
            modifier = Modifier.padding(start = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppInputWithQuantity(
                modifier = Modifier.width(100.dp),
                value = amount,
                unity = unity,
                onValueChange = {
                    onAmountChange(it)
                },
                onUnityChange = onUnityChange,
                placeholder = Strings.AddProduct.amountPlaceholder
            )

            AppInput(
                modifier = Modifier.width(100.dp),
                value = unityPrice,
                onValueChange = { onUnityPriceChange(it) },
                placeholder = Strings.AddProduct.pricePlaceholder,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
    }
}
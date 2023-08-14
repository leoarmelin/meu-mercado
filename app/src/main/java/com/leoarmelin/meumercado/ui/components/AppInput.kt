package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.extensions.stringValue
import com.leoarmelin.meumercado.extensions.toggled
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.meumercado.ui.theme.Blue
import com.leoarmelin.meumercado.ui.theme.GrayThree
import com.leoarmelin.sharedmodels.Unity

@Composable
fun AppInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    rightIcon: (@Composable () -> Unit)? = null,
    placeholder: String
) {
    TextField(
        modifier = modifier.border(1.dp, GrayThree, RoundedCornerShape(16.dp)),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholder)
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = Black,
            placeholderColor = GrayThree,
            backgroundColor = Color.Transparent,
            cursorColor = Blue
        ),
        trailingIcon = rightIcon
    )
}

@Composable
fun AppInputWithQuantity(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    var unityType by remember { mutableStateOf(Unity.UN) }
    AppInput(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        rightIcon = {
            Text(
                text = unityType.stringValue,
                modifier = Modifier.clickable {
                    unityType = unityType.toggled
                }
            )
        }
    )
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
private fun PreviewOne() {
    var text by remember { mutableStateOf("") }

    AppInput(
        modifier = Modifier.padding(16.dp),
        value = text,
        onValueChange = { text = it },
        placeholder = "Nome da categoria"
    )
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
private fun PreviewTwo() {
    var text by remember { mutableStateOf("") }

    AppInputWithQuantity(
        modifier = Modifier.padding(16.dp),
        value = text,
        onValueChange = { text = it },
        placeholder = "Nome da categoria"
    )
}
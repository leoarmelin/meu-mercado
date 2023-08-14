package com.leoarmelin.meumercado.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leoarmelin.meumercado.ui.components.AppInput
import com.leoarmelin.meumercado.ui.components.EmojiTextField
import com.leoarmelin.meumercado.ui.components.ScreenHeader
import com.leoarmelin.meumercado.ui.theme.Blue
import com.leoarmelin.meumercado.ui.theme.GrayOne
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel

@Composable
fun AddCategoryScreen(
    mainViewModel: MainViewModel = viewModel(),
    navigationViewModel: NavigationViewModel = viewModel(),
) {
    var emoji by remember { mutableStateOf("") }
    var categoryName by remember { mutableStateOf("") }
    val checkColor by animateColorAsState(
        targetValue = if (emoji.isEmpty() || categoryName.isEmpty()) GrayOne else Blue,
        label = "checkColor"
    )

    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ScreenHeader(
            title = Strings.AddCategory.title,
            onBack = navigationViewModel::popBack
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EmojiTextField(value = emoji, onValueChange = { emoji = it })

            AppInput(
                modifier = Modifier.weight(1f),
                value = categoryName,
                onValueChange = { categoryName = it },
                placeholder = Strings.AddCategory.namePlaceholder
            )

            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickable(enabled = emoji.isNotEmpty() && categoryName.isNotEmpty()) {
                        mainViewModel.createCategory(emoji, categoryName)
                    },
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = Strings.AddCategory.save,
                tint = checkColor
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewOne() {
    AddCategoryScreen()
}